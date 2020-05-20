/*
 * Copyright (C) 2016 Nishant Srivastava
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package awais.example.screenshottproject;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.screenshott.ScreenShott;
import java.io.File;



public class MainActivity extends AppCompatActivity {

  private final static String[] requestWritePermission =
      { Manifest.permission.WRITE_EXTERNAL_STORAGE };
  private Bitmap bitmap;
  String name;
  String Password="pakistan123";
  String[] myArray = new String[10];
  EditText Name;
  int counter=4;
  private MediaPlayer mp;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    CheckBox checkbox =  findViewById(R.id.checkbox);

    final boolean hasWritePermission = RuntimePermissionUtil.checkPermissonGranted(this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE);
    checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
        if (!isChecked) {
          // show password
          Name.setTransformationMethod(PasswordTransformationMethod.getInstance());
        } else
        {
          // hide password
          Name.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
      }
    });
    Name=findViewById(R.id.editText);
    Name.setCursorVisible(false);
    Name.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Name.setCursorVisible(true);
      }
    });
    Button capture_screenshot = findViewById(R.id.capture_screenshot);
    capture_screenshot.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        // Take screen shot
        InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        name = Name.getText().toString();
          if (name.equals("") || !name.equals(Password)) {
            if (counter == 3) {
              mp = MediaPlayer.create(MainActivity.this, R.raw.warning);
              mp.start();
              final Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.customthre);
              Button Ok = (Button) dialog.findViewById(R.id.Ok);
              Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mp.stop();
                  dialog.hide();
                }
              });
              dialog.show();
              counter = counter - 1;
            } else if (counter == 2) {
              mp = MediaPlayer.create(MainActivity.this, R.raw.warning);
              mp.start();
              final Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.customtwo);
              Button Ok = (Button) dialog.findViewById(R.id.Ok);
              Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mp.stop();
                  dialog.hide();
                }
              });
              dialog.show();
              counter = counter - 1;
            } else if (counter == 1) {
              mp = MediaPlayer.create(MainActivity.this, R.raw.warning);
              mp.start();
              final Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.customone);
              Button Ok = (Button) dialog.findViewById(R.id.Ok);
              Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mp.stop();
                  dialog.hide();
                }
              });
              dialog.show();
              counter = counter - 1;
            } else if (counter == 4) {
              mp = MediaPlayer.create(MainActivity.this, R.raw.warning);
              mp.start();
              final Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.custom);
              Button Ok = (Button) dialog.findViewById(R.id.Ok);
              Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  mp.stop();
                  dialog.hide();
                }
              });
              dialog.show();
              counter = counter - 1;
            }
            else if (counter==0)
            {
              final Dialog dialog = new Dialog(MainActivity.this);
              dialog.setContentView(R.layout.custome);
              Button Ok = (Button) dialog.findViewById(R.id.Ok);
              Ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  dialog.hide();
                }
              });
              dialog.show();
              counter=4;
              bitmap = ScreenShott.getInstance().takeScreenShotOfRootView(view);
              mp = MediaPlayer.create(MainActivity.this, R.raw.shuttersound);
              mp.start();
              if (bitmap != null) {
                if (hasWritePermission) {
                  saveScreenshot();
                } else {
                  RuntimePermissionUtil.requestPermission(MainActivity.this, requestWritePermission, 100);
                }
              }
            }
          }
          else if(name.equals(Password))
          {
            Intent intent = new Intent(MainActivity.this, Home.class);
            startActivity(intent);
            finish();
          }

        //     imageView.setImageBitmap(bitmap);
      }
    });
  }
  public void musicTimerr() {
    mp = MediaPlayer.create(MainActivity.this, R.raw.warning);
    mp.start();
    new CountDownTimer(4000, 1000) {

      public void onTick(long millisUntilFinished) {
      }

      public void onFinish() {
        mp.stop();
      }

    }.start();
  }
  private void saveScreenshot() {
    // Save the screenshot

    try {
      File file = ScreenShott.getInstance()
          .saveScreenshotToPicturesFolder(MainActivity.this, bitmap, "my_screenshot");
      // Display a toast
      Toast.makeText(MainActivity.this, "Secreenshot Saved at " + file.getAbsolutePath(),
          Toast.LENGTH_SHORT).show();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull final String[] permissions,
      @NonNull final int[] grantResults) {
    switch (requestCode) {
      case 100: {

        RuntimePermissionUtil.onRequestPermissionsResult(grantResults, new RPResultListener() {
          @Override
          public void onPermissionGranted() {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
              saveScreenshot();
            }
          }

          @Override
          public void onPermissionDenied() {
            Toast.makeText(MainActivity.this, "Permission Denied! You cannot save image!",
                Toast.LENGTH_SHORT).show();
          }
        });
        break;
      }
    }
  }
}
