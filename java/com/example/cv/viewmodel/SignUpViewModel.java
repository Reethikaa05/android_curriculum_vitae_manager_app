package com.example.cv.viewmodel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignUpViewModel extends ViewModel {

    private final MutableLiveData<String> signUpResult = new MutableLiveData<>();

    public LiveData<String> getSignUpResult() {
        return signUpResult;
    }

    public void signUp(String username, String email, String password) {
        // Simulate a background operation (replace with real async logic)
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network delay

                // Simple validation
                if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
                    signUpResult.postValue("Sign Up successful");
                } else {
                    signUpResult.postValue("Sign Up failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                signUpResult.postValue("Sign Up failed");
            }
        }).start();
    }
}
