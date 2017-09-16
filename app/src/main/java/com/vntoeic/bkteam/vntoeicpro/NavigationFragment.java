package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

import utils.Config;
import utils.ImageHelper;
import utils.LoginUtils;

/**
 * Created by giang on 8/9/17.
 */

public class NavigationFragment extends Fragment implements LoginUtils.OnLoginWithServer, ImageHelper.DownloadImageListener, GoogleApiClient.OnConnectionFailedListener{
    private static final int RC_SIGN_IN = 1;
    private ImageView mLoginGoogle, mLoginFacebook;
    private BaseActivity mContext;
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private View mLoginLayout, mUserLayout;
    private ImageView mUserImg;
    private TextView mUserName;
    private View mLogoutLayout;
    private GoogleApiClient mGoogleApiClient;
    private boolean signInWithGoogle = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());



    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof BaseActivity) this.mContext = (BaseActivity) context;
        else throw new ClassCastException("context must be extends BaseActivity");
        super.onAttach(context);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("250446074415-mtb27n06orqhu396i1pfdhtti1ptrair.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .enableAutoManage(getActivity(), this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        bindView(view);

        return view;
    }

    private void bindView(View view) {
        mLoginLayout = view.findViewById(R.id.login_layout);
        mUserLayout = view.findViewById(R.id.user_login_layout);
        mUserImg = (ImageView) view.findViewById(R.id.user_img);
        mUserName = (TextView) view.findViewById(R.id.username);
        mLoginFacebook = (ImageView) view.findViewById(R.id.loginWithFB);
        mLogoutLayout = view.findViewById(R.id.logout);
        mLogoutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
        mLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithFacebook();
                mContext.onClickMenu();

            }
        });

        mLoginGoogle  = (ImageView) view.findViewById(R.id.loginWithGG);
        mLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithGoogle();
                mContext.onClickMenu();
            }
        });
    }

    private void signOut() {
        mFirebaseAuth.signOut();
        if (signInWithGoogle) Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        else {
            LoginManager.getInstance().logOut();

        }
        updateUI(null);
    }




    private void loginWithGoogle() {


        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    public void loginWithFacebook() {
        mCallbackManager = CallbackManager.Factory.create();
        mLoginManager = LoginManager.getInstance();
        mLoginManager.logInWithReadPermissions(this, Arrays.asList("email"));
        mLoginManager.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {

//                cancelLoginFacebook();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    }

    private void handleFacebookAccessToken(final AccessToken accessToken) {
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuthWithCredential(credential);

    }

    private void firebaseAuthWithCredential(AuthCredential credential) {
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mContext, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            mUser = mFirebaseAuth.getCurrentUser();
                            mUser.getToken(true)
                                    .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<GetTokenResult> task) {
                                            String idToken = task.getResult().getToken();
                                            loginToServer(mUser, idToken);
                                        }
                                    });
                            updateUI(mUser);

                        } else {
                            updateUI(null);
                        }
                    }
                });
    }

    private void loginToServer(FirebaseUser user, String userToken) {
        SharedPreferences pref = getActivity().getSharedPreferences(Config.SHARED_PREF_MESSAGE, 0);
        String messageToken = pref.getString(Config.MESSAGE_TOKEN, null);

        if (messageToken != null) {
            LoginUtils.ModelUserLogin userLogin = new LoginUtils.ModelUserLogin(user,userToken, messageToken);
            LoginUtils.LoginWithServer loginWithServer = new LoginUtils.LoginWithServer(this);
            loginWithServer.execute(userLogin);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleGoogleSignInResult(result);
        }
        else mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    private void handleGoogleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            if (account.getAccount() == null) return;
            signInWithGoogle = true;
            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
            firebaseAuthWithCredential(credential);
        } else {

            updateUI(null);
        }
    }

    public void updateUI(FirebaseUser user) {
        if (user == null) {
            mLoginLayout.setVisibility(View.VISIBLE);
            mUserLayout.setVisibility(View.GONE);
            mLogoutLayout.setVisibility(View.GONE);
            Toast.makeText(mContext, "Authentication failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mContext, "Authentication success", Toast.LENGTH_SHORT).show();
            mLoginLayout.setVisibility(View.GONE);
            mUserLayout.setVisibility(View.VISIBLE);
            mLogoutLayout.setVisibility(View.VISIBLE);

            setUserInfo(user);
        }
    }

    private void setUserInfo(FirebaseUser user) {

        String name = user.getDisplayName();
        Uri photoUrl = user.getPhotoUrl();
        mUserName.setText(name);

        ImageHelper.DownloadImage downloadImage = new ImageHelper.DownloadImage(this);
        assert photoUrl != null;
        downloadImage.execute(photoUrl.toString());
    }

    @Override
    public void onDownloadSuccess(Bitmap bitmap) {
        if (bitmap != null) mUserImg.setImageDrawable(new ImageHelper.RoundImage(bitmap, mUserImg));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
