package com.student.smartETailor.interfaces;

public interface DesignPictureUploadingInterface {
    void picUploaded(String picURL, int position);

    void picUploadingError();

    void picUploadingCompleted(int size);
}