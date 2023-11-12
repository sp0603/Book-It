package com.example.scheduling;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentStateAdapter{

    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();


    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
        addFragments();
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // responsible for creating and returning a fragment for a specific position within ViewPager2

        return fragmentArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        return fragmentArrayList.size();
    }
    
    private void addFragments() {
        fragmentArrayList.add(new HomeFragment());
        fragmentArrayList.add(new CreateEvent());
        fragmentArrayList.add(new ContactsPage());
        fragmentArrayList.add(new ProfileFragment());
    }
    public void setUpLayout(ViewPager2 viewPager, TabLayout tabLayout, String[] tabNames) {
        viewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);

        viewPager.setAdapter(this);

        new TabLayoutMediator(
                tabLayout,
                viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText(tabNames[position]);
                    }
                }
        ).attach();
    }
}
