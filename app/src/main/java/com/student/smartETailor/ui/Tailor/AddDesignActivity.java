package com.student.smartETailor.ui.Tailor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.student.smartETailor.R;
import com.student.smartETailor.adapters.MeasurementsAdapter;
import com.student.smartETailor.adapters.TailorAddDesignsListAdapter;
import com.student.smartETailor.constants.Const;
import com.student.smartETailor.interfaces.AddPaymentInterface;
import com.student.smartETailor.interfaces.DesignPictureUploadingInterface;
import com.student.smartETailor.ml.ClothModels;
import com.student.smartETailor.models.Design;
import com.student.smartETailor.models.Measurement;
import com.student.smartETailor.ui.MainActivity;
import com.student.smartETailor.utils.DialogsUtils;
import com.student.smartETailor.utils.FBUtils;
import com.student.smartETailor.utils.UsersUtils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;

public class AddDesignActivity extends AppCompatActivity {
    private final String TAG = AddDesignActivity.class.getSimpleName();

    RecyclerView RVImages, RVMeasurements;
    TailorAddDesignsListAdapter imagesAdapter;
    MeasurementsAdapter measurementsAdapter;

    EditText etName, etDescription;
    TextView tvAddDesign;
    CardView cardMeasurementsNA;
    CheckBox checkCustomMeasurements;

    ProgressDialog progressDialog;
    private EditText etCulture;

    String designCustomizePrice = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_design);

        settings();
        fetchMeasurements();

        checkCustomMeasurements.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    DialogsUtils.getInstance().showDialogForMeasurementPrice(AddDesignActivity.this, new AddPaymentInterface() {
                        @Override
                        public void paymentConfirmed(String payment) {
                            designCustomizePrice = payment;
                        }

                        @Override
                        public void onCancelled() {
                            designCustomizePrice = "";
                            checkCustomMeasurements.setChecked(false);
                        }
                    });
                } else {
                    designCustomizePrice = "";
                }
            }
        });

        tvAddDesign.setOnClickListener(view -> {
            String name = etName.getText().toString();
            String desc = etDescription.getText().toString();
            if (name.isEmpty()) {
                etName.setError("Please enter the design name");
                return;
            } else if (desc.isEmpty()) {
                etDescription.setError("Please enter the design description");
                return;
            } else if (imagesAdapter.getItemCount() <= 1) {
                Toast.makeText(this, "Please select some design images", Toast.LENGTH_SHORT).show();
                return;
            } else if (measurementsAdapter.getSelectedMeasurements().size() <= 0) {
                Toast.makeText(this, "Please select some measurements", Toast.LENGTH_SHORT).show();
                return;
            }

            String UID = UsersUtils.getInstance(AddDesignActivity.this).fetchUser().getUID();
            Design design = new Design();
            design.setName(name);
            design.setDescription(desc);
            design.setTailorID(UID);
            design.setDesignID(UID + System.currentTimeMillis());
            design.setMeasurements(measurementsAdapter.getSelectedMeasurements());

            design.setCustomization(checkCustomMeasurements.isChecked());
            design.setPrice(designCustomizePrice);

            progressDialog.show();
            FBUtils.getInstance().uploadDesigns(UID, imagesAdapter.getImages(), 0, new DesignPictureUploadingInterface() {
                @Override
                public void picUploaded(String picURL, int position) {
                    design.addDesign(picURL);
                }

                @Override
                public void picUploadingError() {
                    progressDialog.dismiss();
                    Toast.makeText(AddDesignActivity.this, "Something went wrong in uploading pic", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void picUploadingCompleted(int size) {
                    progressDialog.dismiss();
                    design.setCulture(etCulture.getText().toString().trim());
                    FBUtils.getInstance().uploadDesign(AddDesignActivity.this, design);
                }
            });

        });
    }

    private void fetchMeasurements() {
        FirebaseDatabase.getInstance().getReference(Const.DB_USERS)
                .child(UsersUtils.getInstance(this).fetchUser().getUID())
                .child(Const.DB_USERS_MEASUREMENTS)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            ArrayList<Measurement> measurements = new ArrayList<>();
                            for (DataSnapshot snap : snapshot.getChildren()) {
                                Measurement m = snap.getValue(Measurement.class);
                                measurements.add(m);
                            }
                            if (measurements.size() > 0) {
                                cardMeasurementsNA.setVisibility(View.GONE);
                                measurementsAdapter.updateMeasurements(measurements);
                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e(TAG, error.toString());
                    }
                });
    }

    private void settings() {
        etCulture = findViewById(R.id.et_culture);
        etName = findViewById(R.id.et_design_name);
        etDescription = findViewById(R.id.et_design_description);
        tvAddDesign = findViewById(R.id.tv_add_design);
        cardMeasurementsNA = findViewById(R.id.card_measurement_NA);
        RVImages = findViewById(R.id.rv_design_images);
        RVMeasurements = findViewById(R.id.rv_design_measurements);
        checkCustomMeasurements = findViewById(R.id.check_custom_measurements);
        imagesAdapter = new TailorAddDesignsListAdapter(this);
        RVImages.setAdapter(imagesAdapter);
        measurementsAdapter = new MeasurementsAdapter(this, true);
        RVMeasurements.setAdapter(measurementsAdapter);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Uploading");
        progressDialog.setMessage("Please wait. Your design is uploading...");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uriImage = CropImage.getPickImageResultUri(this, data);
            final InputStream imageStream;
            try {
                imageStream = AddDesignActivity.this.getContentResolver().openInputStream(uriImage);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                runModel(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            imagesAdapter.addNewImage(uriImage);
        }
    }

    private void runModel(Bitmap inputImage) {

        Bitmap bitmap = Bitmap.createScaledBitmap(inputImage, 224, 224, true);
        ByteBuffer inputBuffer = ByteBuffer.allocateDirect(224 * 224 * 3 * 4).order(ByteOrder.nativeOrder());
        for (int y = 0; y < 224; y++) {
            for (int x = 0; x < 224; x++) {
                int px = bitmap.getPixel(x, y);

                // Get channel values from the pixel value.
                int r = Color.red(px);
                int g = Color.green(px);
                int b = Color.blue(px);

                // Normalize channel values to [-1.0, 1.0]. This requirement depends
                // on the model. For example, some models might require values to be
                // normalized to the range [0.0, 1.0] instead.
                float rf = (r - 127) / 255.0f;
                float gf = (g - 127) / 255.0f;
                float bf = (b - 127) / 255.0f;

                inputBuffer.putFloat(rf);
                inputBuffer.putFloat(gf);
                inputBuffer.putFloat(bf);
            }
        }

        try {
            ClothModels model = ClothModels.newInstance(this);

            // Creates inputs for reference.
            TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1, 224, 224, 3}, DataType.FLOAT32);
            inputFeature0.loadBuffer(inputBuffer);

            // Runs model inference and gets result.
            ClothModels.Outputs outputs = model.process(inputFeature0);
            TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();
//            tvOutput.setText(Arrays.toString(outputFeature0.getFloatArray()));
            // Releases model resources if no longer used.
            model.close();
            float floats[] = outputFeature0.getFloatArray();
            int[] ints = new int[floats.length];

            for (int i = 0; i < floats.length; i++) {
                ints[i] = (int) Math.round(floats[i]);
            }

            int position = -1;
            for (int i = 0; i < floats.length; i++) {
                if (ints[i] == 1) {
                    position = i;
                    break;
                }
            }

            switch (position) {
                case 0:
                    etCulture.setText("Chinese");
                    break;
                case 1:
                    etCulture.setText("Europe");
                    break;
                case 2:
                default:
                    etCulture.setText("Pakistan");
                    break;
                case 3:
                    etCulture.setText("Indian");
                    break;


            }

        } catch (IOException e) {
            // TODO Handle the exception
        }
    }
}