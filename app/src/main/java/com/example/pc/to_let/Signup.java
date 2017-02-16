package com.example.pc.to_let;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;

public class Signup extends AppCompatActivity implements View.OnClickListener {
    EditText name, address, phone, email;
    FloatingActionButton fab;
    CardView cvAdd;
    private Bitmap b;
    ImageButton profilepic;
    Button take_image, choose_image, cancle;
    private static final int RESULT_LOAD_IMAGE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        allfind();
        allclick();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ShowEnterAnimation();
        }
        Camera_parmition_Alow();
        External_storage_alow();
    }

    private void allfind() {
        fab = (FloatingActionButton) findViewById(R.id.fab);
        cvAdd = (CardView) findViewById(R.id.cv_add);
        profilepic = (ImageButton) findViewById(R.id.profile_pic);


    }

    private void allclick() {
        fab.setOnClickListener(this);
        profilepic.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.profile_pic:
                AlertDialog.Builder builder = new AlertDialog.Builder(Signup.this);
                View mView = getLayoutInflater().inflate(R.layout.image_choices_dialog, null);
                take_image = (Button) mView.findViewById(R.id.takePhoto);
                choose_image = (Button) mView.findViewById(R.id.choosePhoto);
                cancle = (Button) mView.findViewById(R.id.cnacel);


                take_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openCamera();
                    }
                });
                choose_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        openGallery();



                    }
                });

                builder.setView(mView);
                final AlertDialog dialog = builder.create();
                dialog.show();
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();

                    }
                });
                break;

            case R.id.fab:
                animateRevealClose();
                break;
        }
    }

    private void openGallery() {
        Intent takePictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra("crop",true);
            takePictureIntent.putExtra("aspectX", 1);
            takePictureIntent.putExtra("aspectY", 1);
            takePictureIntent.putExtra("outputX", 150);
            takePictureIntent.putExtra("outputY", 150);
            takePictureIntent.putExtra("return-data", true);

            startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);
        }
    }

    private void openAlbum() {
        Intent openAlbumIntent = new Intent(Intent.ACTION_CHOOSER);
        openAlbumIntent.setType("image/*");
        openAlbumIntent.putExtra("crop",true);
        openAlbumIntent.putExtra("aspectX", 1);
        openAlbumIntent.putExtra("aspectY", 1);
        openAlbumIntent.putExtra("outputX", 150);
        openAlbumIntent.putExtra("outputY", 150);
        openAlbumIntent.putExtra("return-data", true);
        startActivityForResult(openAlbumIntent, RESULT_LOAD_IMAGE);

    }
    private void openCamera(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            takePictureIntent.putExtra("crop",true);
            takePictureIntent.putExtra("aspectX", 1);
            takePictureIntent.putExtra("aspectY", 1);
            takePictureIntent.putExtra("outputX", 150);
            takePictureIntent.putExtra("outputY", 150);
            takePictureIntent.putExtra("return-data", true);
            startActivityForResult(takePictureIntent, RESULT_LOAD_IMAGE);
        }
    }

    private void Camera_parmition_Alow() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, RESULT_LOAD_IMAGE);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, RESULT_LOAD_IMAGE);
            }
        }
    }

    private void External_storage_alow() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,  Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,  Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);

            } else {

                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_LOAD_IMAGE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data != null) {
            b = (Bitmap) data.getExtras().get("data");
            profilepic.setImageBitmap(b);

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ShowEnterAnimation() {
        Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.fabtransition);
        getWindow().setSharedElementEnterTransition(transition);

        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {
                cvAdd.setVisibility(View.GONE);
            }

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);
                animateRevealShow();
            }

            @Override
            public void onTransitionCancel(Transition transition) {

            }

            @Override
            public void onTransitionPause(Transition transition) {

            }

            @Override
            public void onTransitionResume(Transition transition) {

            }


        });

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealShow() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, fab.getWidth() / 2, cvAdd.getHeight());
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                cvAdd.setVisibility(View.VISIBLE);
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void animateRevealClose() {
        Animator mAnimator = ViewAnimationUtils.createCircularReveal(cvAdd, cvAdd.getWidth() / 2, 0, cvAdd.getHeight(), fab.getWidth() / 2);
        mAnimator.setDuration(500);
        mAnimator.setInterpolator(new AccelerateInterpolator());
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                cvAdd.setVisibility(View.INVISIBLE);
                super.onAnimationEnd(animation);
                fab.setImageResource(R.drawable.plus);
                Signup.super.onBackPressed();
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
            }
        });
        mAnimator.start();

    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBackPressed() {
        animateRevealClose();
    }

}

