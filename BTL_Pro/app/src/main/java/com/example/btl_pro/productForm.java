package com.example.btl_pro;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class productForm extends AppCompatActivity {
    // data edit
    String dTenSp;
    String dSdt;
    String dGiatien;
    String dSrcImage;

    productActionDB productActionDB;
    EditText namePro;
    EditText phone;
    EditText cost;
    Button addProduct;
    Button pButtonChooseImage;
    ImageView pImageView;

    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;


    public static final int MY_REQUEST_CODE = 100;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_form);
        constructorArrT();
        EditProduct();
        bindBtnAddProduct();
    }

    public void constructorArrT() {
        namePro = (EditText) findViewById(R.id.namePro);
        phone = (EditText) findViewById(R.id.phone);
        cost = (EditText) findViewById(R.id.cost);
        addProduct = (Button) findViewById(R.id.addProduct);
        pButtonChooseImage = (Button) findViewById(R.id.button_choose_file);
        pImageView = (ImageView) findViewById(R.id.image_view);

        Intent intent = getIntent();
        dTenSp = intent.getStringExtra("dTenSp");
        dSdt = intent.getStringExtra("dSdt");
        dGiatien = intent.getStringExtra("dGiatien");
        dSrcImage = intent.getStringExtra("dSrcImage");

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        pButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenFileChooser();
            }
        });
    }

    private void OpenFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, MY_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).into(pImageView);
        }
    }

    public void bindBtnAddProduct() {
        if (!validateBeforeSave()) {
            return;
        }
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (phone.getText().toString().length() < 10 || phone.getText().toString().length() > 13 ||
                        commonUtil.validateNumber(phone.getText().toString())) {
                    Toast.makeText(productForm.this, "Lỗi nhập số điện thoại", Toast.LENGTH_SHORT).show();
                    phone.requestFocus();
                    return;
                }
                productActionDB = new productActionDB(productForm.this);
                Integer id = productActionDB.getIdNewProduct();
                String tenSp = namePro.getText().toString();
                String tenNb = commonUtil.userName;
                String dt = phone.getText().toString();
                Integer gia = Integer.valueOf(cost.getText().toString());
                String srcImage = mImageUri.toString();
                productBean bean = new productBean(id, tenSp, tenNb, dt, gia, srcImage, true );
                if (!commonUtil.isNullOrEmpTy(dTenSp) && !commonUtil.isNullOrEmpTy(dSdt)
                        && !commonUtil.isNullOrEmpTy(dGiatien) && !commonUtil.isNullOrEmpTy(dSrcImage)){
                    uploadFile(bean);
                    long newId = productActionDB.update(bean);
                    if (newId > 0L) {
                        Toast.makeText(productForm.this, "Cập nhật thành công!",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(productForm.this, productController.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(productForm.this, "Có lỗi xảy ra!",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    uploadFile(bean);
                    long newId = productActionDB.insert(bean);
                    if (newId > 0L) {
                        Toast.makeText(productForm.this, "Thêm mới thành công!",
                                Toast.LENGTH_LONG).show();
                        Intent i = new Intent(productForm.this, productController.class);
                        startActivity(i);
                    } else {
                        Toast.makeText(productForm.this, "Có lỗi xảy ra!",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
    public boolean validateBeforeSave() {
        if (commonUtil.isNullOrEmpTy(namePro.getText().toString())) {
            Toast.makeText(this, "Tên sản phẩm không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (commonUtil.isNullOrEmpTy(phone.getText().toString())) {
            Toast.makeText(this, "Số điện thoại không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (commonUtil.isNullOrEmpTy(cost.getText().toString())) {
            Toast.makeText(this, "Giá tiền không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (commonUtil.isNullOrEmpTy(pImageView.getResources().toString())) {
            Toast.makeText(this, "Ảnh không được để trống!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void EditProduct() {
        if (!commonUtil.isNullOrEmpTy(dTenSp) && !commonUtil.isNullOrEmpTy(dSdt)
                && !commonUtil.isNullOrEmpTy(dGiatien) && !commonUtil.isNullOrEmpTy(dSrcImage)) {
            addProduct.setText("Lưu");
            namePro.setText(dTenSp);
            phone.setText(dSdt);
            cost.setText(dGiatien);
            Picasso.get().load(Uri.parse(dSrcImage)).into(pImageView);
        } else {
            addProduct.setText("Thêm sản phẩm");
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
    private void uploadFile(final productBean productBean) {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis() +
                    "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    String fileName = commonUtil.userName + "_"
                            + productBean.getNamePro().trim();
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // get upload current
//                        Upload upload = new Upload(fileName,
//                                taskSnapshot.getStorage().getDownloadUrl().toString());
//                        String uploadId = mDatabaseRef.push().getKey();
//                        mDatabaseRef.child(uploadId).setValue(upload);
                        productBean.setSrcImage(taskSnapshot.getStorage().getDownloadUrl().toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(productForm.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        } else {
            Toast.makeText(this, "File ảnh không tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }
}
