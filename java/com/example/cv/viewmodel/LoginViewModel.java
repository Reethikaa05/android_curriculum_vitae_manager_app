package com.example.cv.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private final MutableLiveData<String> loginResult = new MutableLiveData<>();

    public LiveData<String> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // Simulate a background operation (replace with real async logic)
        new Thread(() -> {
            try {
                Thread.sleep(2000); // Simulate network delay

                // Simple validation
                if ("user".equals(username) && "pass".equals(password)) {
                    loginResult.postValue("Login successful");
                } else {
                    loginResult.postValue("Login failed");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                loginResult.postValue("Login failed");
            }
        }).start();
    }
}
