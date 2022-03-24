package com.example.contactapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.avatarfirst.avatargenlib.AvatarGenerator;
import com.example.contactapp.databinding.ActivityNewContactBinding;
import com.github.drjacky.imagepicker.ImagePicker;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Random;


public class NewContactActivity extends AppCompatActivity {
    private static final String TAG = "NewContactActivity";
    private ActivityNewContactBinding binding;
    private ActionBar actionBar;
    private ContactViewModel mContactVM;
    private AlertDialog.Builder window;
    private boolean editState = false;
    private Bitmap bitmapAvatar;
    private Contact contact = new Contact();
    private final String[] Options = {"Take photo", "Choose photo"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNewContactBinding.inflate(getLayoutInflater());
        View viewRoot = binding.getRoot();
        setContentView(viewRoot);
        try {
            initValues();
        } catch (Exception e){
            Log.e(TAG,e.toString());
        }
    }
    private void initValues(){
        Gson gson = new Gson();
        Intent i = getIntent();
        Contact item = gson.fromJson(i.getStringExtra("contactItem"),Contact.class);
        mContactVM = new ViewModelProvider(this).get(ContactViewModel.class);
        setSupportActionBar(binding.toolbarNewcontact);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            if (item!=null){
                actionBar.setTitle("Edit contact");
                editContact(item);
            } else {
                actionBar.setTitle("Create new contact");
            }

            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }
        String[] phoneItems = new String[] { "Mobile", "Home", "Work" };
        String[] emailItems = new String[] { "Home", "Work", "Other"};
        ArrayAdapter<String> adapterPhone = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, phoneItems);
        ArrayAdapter<String> adapterEmail = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, emailItems);
        binding.spinnerEmail.setAdapter(adapterEmail);
        binding.spinnerPhone.setAdapter(adapterPhone);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = binding.etFirst.getText().toString();
                String lname = binding.etLast.getText().toString();
                String phone = binding.editTextPhone.getText().toString();
                String email = binding.editTextTextEmailAddress.getText().toString();
                if (!editState){
                    if (!(fname + lname).equals("") && !phone.equals("")){
                        if (bitmapAvatar == null){
                            Random rnd = new Random();
                            int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                            BitmapDrawable ava = new AvatarGenerator.AvatarBuilder(getApplicationContext())
                                    .setLabel(fname)
                                    .setAvatarSize(120)
                                    .setTextSize(30)
                                    .toCircle()
                                    .setBackgroundColor(color)
                                    .build();
                            bitmapAvatar = ava.getBitmap();
                        }
                        Contact contact = new Contact(bitmapAvatar, fname, lname, phone, email);
                        mContactVM.insertContact(contact);
                    } else {
                        finish();
                    }
                } else {
                    contact.setFirstname(fname);
                    contact.setLastname(lname);
                    contact.setEmail(email);
                    contact.setPhone(phone);
                    if (bitmapAvatar != null){
                        contact.setAvatar(bitmapAvatar);
                    }
                    mContactVM.updateContact(contact);
                }
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        binding.ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    popupDialog();
                } catch(Exception e){
                    Log.e(TAG,e.getMessage());
                }
            }
        });
    }
    ActivityResultLauncher<Intent> launcher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (ActivityResult result) -> {
                if (result.getResultCode() == RESULT_OK) {
                    Uri uri = result.getData().getData();
                    // Use the uri to load the image
//                    binding.ivAvatar.setImageURI(uri);
                    try {
                        bitmapAvatar = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        binding.ivAvatar.setImageBitmap(bitmapAvatar);
                    } catch (IOException e) {
                        Log.e(TAG,e.getMessage());
                    }
                    binding.ivAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
                } else if (result.getResultCode() == ImagePicker.RESULT_ERROR) {
                    // Use ImagePicker.Companion.getError(result.getData()) to show an error
                    Log.e(TAG,"camera: error occurred");
                }
            });
    public Intent getCamera(){
        return ImagePicker.with(this)
                .cameraOnly()	//User can only capture image using Camera
                .createIntent();
    }
    public Intent getGallery(){
        return ImagePicker.with(this)
                .galleryOnly()	//User can only select image from Gallery
                .createIntent();
    }

    public void popupDialog(){
        window = new AlertDialog.Builder(this);
        window.setTitle("Change photo");
        window.setItems(Options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    //first option clicked, do this...
                    Intent cam = getCamera();
                    launcher.launch(cam);
                }else if(which == 1){
                    //second option clicked, do this...
                    Intent cam = getGallery();
                    launcher.launch(cam);
                }else{
                    //theres an error in what was selected
                    Toast.makeText(getApplicationContext(), "Hmmm I messed up. I detected that you clicked on : " + which + "?", Toast.LENGTH_LONG).show();
                }
            }
        });

        window.show();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_newcontact,menu);
        return true;
    }
    public void editContact(Contact item){
        editState = true;
        binding.etFirst.setText(item.getFirstname());
        binding.etLast.setText(item.getLastname());
        binding.editTextPhone.setText(item.getPhone());
        binding.editTextTextEmailAddress.setText(item.getEmail());
        if (item.getAvatar().getByteCount() != 57600){
            binding.ivAvatar.setImageBitmap(item.getAvatar());
            binding.ivAvatar.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
        this.contact = item;
    }

}