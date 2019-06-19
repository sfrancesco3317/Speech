package com.example.Memorize;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.Text;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

import java.io.File;
import java.io.FileNotFoundException;

public class OCR extends AppCompatActivity {

        private static final String LOG_TAG = "Text API";
        private static final int PHOTO_REQUEST = 10;
        private TextView scanResults;
        private Uri imageUri;
        private TextRecognizer detector;
        private static final int REQUEST_WRITE_PERMISSION = 20;
        private static final String SAVED_INSTANCE_URI = "uri";
        private static final String SAVED_INSTANCE_RESULT = "result";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_ocr);
            Button button = (Button) findViewById(R.id.buttonOCR);
            scanResults = (TextView) findViewById(R.id.results);
            if (savedInstanceState != null) {
                imageUri = Uri.parse(savedInstanceState.getString(SAVED_INSTANCE_URI));
                scanResults.setText(savedInstanceState.getString(SAVED_INSTANCE_RESULT));
            }
            detector = new TextRecognizer.Builder(getApplicationContext()).build();

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ActivityCompat.requestPermissions(OCR.this, new
                            String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
                }
            });
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            switch (requestCode) {
                case REQUEST_WRITE_PERMISSION:
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        takePicture();
                    } else {
                        Toast.makeText(OCR.this, "Permesso rifiutato!", Toast.LENGTH_SHORT).show();
                    }
            }
        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            Intent i= new Intent(getString(R.string.LAUNCH_OUTPUT_OCR_ACTIVITY));
            Intent errorIntent = new Intent(getString((R.string.LAUNCH_MAINACTIVITY)));
            if (requestCode == PHOTO_REQUEST && resultCode == RESULT_OK) {
                launchMediaScanIntent();
                try {
                    Bitmap bitmap = decodeBitmapUri(this, imageUri);
                    if (detector.isOperational() && bitmap != null) {
                        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
                        SparseArray<TextBlock> textBlocks = detector.detect(frame);
                        String blocks = "";
                        String lines = "";
                        String words = "";
                        for (int index = 0; index < textBlocks.size(); index++) {
                            //estraggo il blocco
                            TextBlock tBlock = textBlocks.valueAt(index);
                            blocks = blocks + tBlock.getValue() + "\n" + "\n";
                            for (Text line : tBlock.getComponents()) {
                                //estraggo le linee
                                lines = lines + line.getValue() + "\n";
                                for (Text element : line.getComponents()) {
                                    //estraggo le parole
                                    words = words + element.getValue() + " ";
                                }
                            }
                        }
                        if (textBlocks.size() == 0) {
                            scanResults.setText("Operazione fallita: testo non trovato!");
                        } else {
                            scanResults.setText(scanResults.getText() + words + "\n");
                            i.putExtra(getString(R.string.STRINGA_TESTO_RIFERIMENTO_INPUT), words);
                            startActivity(i);
                        }
                    } else {
                        scanResults.setText("Impossibile avviare la fotocamera!");
                        startActivity(errorIntent);
                    }
                } catch (Exception e) {
                    Toast.makeText(this, "Impossibile caricare l'immagine!", Toast.LENGTH_SHORT)
                            .show();
                    Log.e(LOG_TAG, e.toString());
                }
            }
        }

        private void takePicture() {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File photo = new File(Environment.getExternalStorageDirectory(), "picture.jpg");
            imageUri = FileProvider.getUriForFile(OCR.this,
                    BuildConfig.APPLICATION_ID + ".provider", photo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            startActivityForResult(intent, PHOTO_REQUEST);
        }

        @Override
        protected void onSaveInstanceState(Bundle outState) {
            if (imageUri != null) {
                outState.putString(SAVED_INSTANCE_URI, imageUri.toString());
                outState.putString(SAVED_INSTANCE_RESULT, scanResults.getText().toString());
            }
            super.onSaveInstanceState(outState);
        }

        private void launchMediaScanIntent() {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            mediaScanIntent.setData(imageUri);
            this.sendBroadcast(mediaScanIntent);
        }

        private Bitmap decodeBitmapUri(Context ctx, Uri uri) throws FileNotFoundException {
            int targetW = 600;
            int targetH = 600;
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            bmOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(ctx.getContentResolver().openInputStream(uri), null, bmOptions);
            int photoW = bmOptions.outWidth;
            int photoH = bmOptions.outHeight;

            int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
            bmOptions.inJustDecodeBounds = false;
            bmOptions.inSampleSize = scaleFactor;

            return BitmapFactory.decodeStream(ctx.getContentResolver()
                    .openInputStream(uri), null, bmOptions);
        }

    }
