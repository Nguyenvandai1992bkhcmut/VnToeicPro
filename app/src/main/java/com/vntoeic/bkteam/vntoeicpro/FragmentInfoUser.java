package com.vntoeic.bkteam.vntoeicpro;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by dainguyen on 5/29/17.
 */

public class FragmentInfoUser extends Fragment {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private GoogleSignInOptions gso;
    SignInButton signInButton;
    GoogleApiClient mGoogleApiClient;
    private ImageView img_avatar;
    private TextView text_name;
    private int isSign=1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_info_user, container, false);
        img_avatar = (ImageView)view.findViewById(R.id.img_avatar);
        text_name =(TextView)view.findViewById(R.id.text_name);
        signInButton = (SignInButton) view.findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);


        if(isSign==0)signInButton.setEnabled(false);
        updateUI(user);


        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, 99);
            }
        });
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if(user==null){
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build();
            mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                    .enableAutoManage(getActivity(), new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            updateUI(null);
                            return;
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();


        }else{
            isSign=0;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 99) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        //Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();
            AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = mAuth.getCurrentUser();
                                updateUI(user);
                            } else {
                                // If sign in fails, display a message to the user.
                                updateUI(null);
                            }

                            // ...
                        }
                    });

        } else {
            // Signed out, show unauthenticated UI.
            //updateUI(false);
        }
    }

    public void updateUI(FirebaseUser user){
        if(user==null){
            text_name.setText("Anonymous");
            img_avatar.setImageResource(R.mipmap.essentialwordsicon);
        }else{
            text_name.setText(user.getDisplayName());
            MyTask myTask = new MyTask();
            myTask.execute(user.getPhotoUrl());
        }
    }

    public class  MyTask extends AsyncTask<Uri,Bitmap,Bitmap> {

        @Override
        protected Bitmap doInBackground(Uri... params) {
            Bitmap bitmap=getBitmapFromURL(params[0]);
            return bitmap;

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap==null)return;
            Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 300, 300, false);
            final BitmapShader bitmapShader = new BitmapShader(bitmap1, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            Drawable drawable = new Drawable() {
                @Override
                public void draw(Canvas canvas) {
                    Paint paint = new Paint();
                    paint.setShader(bitmapShader);
                    paint.setAntiAlias(true);
                    paint.setStyle(Paint.Style.FILL);
                    canvas.drawCircle(150, 150, 150, paint);
                }

                @Override
                public void setAlpha(int alpha) {

                }

                @Override
                public void setColorFilter(ColorFilter colorFilter) {

                }

                @Override
                public int getOpacity() {
                    return 0;
                }
            };
            img_avatar.setImageDrawable(drawable);

        }

        public Bitmap getBitmapFromURL(Uri src) {


            try {
                URL url = new URL(src.getScheme(), src.getHost(), src.getPath());
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap bitmap = BitmapFactory.decodeStream(input);
                return bitmap;

            } catch (IOException e) {
                //  img_avatar.setImageResource(R.mipmap.essentialwordsicon);
                e.printStackTrace();
            }
            return null;

        }

    }

}