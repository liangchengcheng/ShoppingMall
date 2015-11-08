package cxsjcj.hdsx.com.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import cxsjcj.hdsx.com.myapplication.R;
import cxsjcj.hdsx.com.view.CustWebView;

public class VerticalFragment3 extends Fragment {

	private View progressBar;
	private CustWebView webview;
	private boolean hasInited = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.vertical_fragment3, null);
		ViewPager viewPager = (ViewPager)rootView. findViewById(R.id.viewpager);
		TabLayout tabLayout = (TabLayout)rootView. findViewById(R.id.tabs);
		if (viewPager != null) {
			setupViewPager(viewPager);
			tabLayout.setTabTextColors(Color.BLACK, getColorPrimary());
			tabLayout.setupWithViewPager(viewPager);
		}

		return rootView;
	}
	public int getColorPrimary(){
		TypedValue typedValue = new  TypedValue();
		getActivity().getTheme().resolveAttribute(R.attr.colorPrimary, typedValue, true);
		return typedValue.data;
	}
	public void initView() {

	}

	//设置viewpager的布局
	private void setupViewPager(ViewPager viewPager) {
		Adapter adapter = new Adapter(getActivity().getSupportFragmentManager());
		adapter.addFragment(new PictureandDeiectionFragment(), "图文详情");
		adapter.addFragment(new ProductParameterFragment(), "产品参数");
		adapter.addFragment(new ShopperMasterFragment(), "店铺推荐");
		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(0);
	}

	static class Adapter extends FragmentPagerAdapter {
		private final List<Fragment> mFragments = new ArrayList<>();
		private final List<String> mFragmentTitles = new ArrayList<>();

		public Adapter(FragmentManager fm) {
			super(fm);
		}

		public void addFragment(Fragment fragment, String title) {
			mFragments.add(fragment);
			mFragmentTitles.add(title);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return mFragmentTitles.get(position);
		}
	}

}
