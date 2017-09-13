package com.vntoeic.bkteam.vntoeicpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.vntoeic.bkteam.vntoeicpro.BaseActivity;
import com.vntoeic.bkteam.vntoeicpro.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.Config;
import utils.LoginUtils;

/**
 * Created by giang on 8/9/17.
 */

public class NavigationFragment extends Fragment implements LoginUtils.OnLoginWithServer{
    private ImageView mLoginGoogle, mLoginFacebook;
    private BaseActivity mBaseActivity;
    private CallbackManager mCallbackManager;
    private LoginManager mLoginManager;
    private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity());
    }

    @Override
    public void onAttach(Context context) {
        if (context instanceof BaseActivity) this.mBaseActivity = (BaseActivity) context;
        else throw new ClassCastException("context must be extends BaseActivity");
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_navigation, container, false);
        bindView(view);


        return view;
    }

    private void bindView(View view) {
        mLoginFacebook = (ImageView) view.findViewById(R.id.loginWithFB);
        mLoginFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginWithFacebook();
            }
        });

        mLoginGoogle  = (ImageView) view.findViewById(R.id.loginWithGG);
        mLoginGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


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
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(mBaseActivity, new OnCompleteListener<AuthResult>() {
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
//                            updateUI(mUser);

                        } else {

//                            updateUI(null);
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
        mCallbackManager.onActivityResult(requestCode, resultCode, data);

    }

    public void updateUI(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(mBaseActivity, "Authentication failed.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(mBaseActivity, "Authentication success", Toast.LENGTH_SHORT).show();
        }
    }
}
