package com.example.eventgo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.eventgo.databinding.SecondPageBinding;
import com.example.eventgo.eventFragment;
import com.example.eventgo.homeFragment;
import com.example.eventgo.menuFragment;
import com.example.eventgo.notificationFragment;
import com.example.eventgo.profileFragment;
import com.example.eventgo.databinding.GetStartedBinding;

import java.util.ArrayList;
import java.util.List;

public class second_page extends AppCompatActivity {
    private SecondPageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = SecondPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new homeFragment());

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {

            switch (item.getItemId()){
                case R.id.home:
                    replaceFragment(new homeFragment());
                    break;
                case R.id.event:
                    replaceFragment(new eventFragment());
                    break;
                case R.id.notification:
                    replaceFragment(new notificationFragment());
                    break;
                case R.id.profile:
                    replaceFragment(new profileFragment());
                    break;
                case R.id.menu:
                    replaceFragment(new menuFragment());
                    break;

            }




            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout,fragment);
        fragmentTransaction.commit();
    }
}