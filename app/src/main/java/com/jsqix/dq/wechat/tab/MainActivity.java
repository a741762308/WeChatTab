package com.jsqix.dq.wechat.tab;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.jfeinstein.jazzyviewpager.JazzyViewPager;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.SlideCallback;
import com.jfeinstein.jazzyviewpager.JazzyViewPager.TransitionEffect;
import com.jsqix.dq.wechat.tab.fragment.BalanceFragment;
import com.jsqix.dq.wechat.tab.fragment.HomeFragment;
import com.jsqix.dq.wechat.tab.fragment.OrderFragment;
import com.jsqix.dq.wechat.tab.fragment.SetFragment;
import com.jsqix.utils.BaseActivity;
import com.nineoldandroids.view.ViewHelper;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {
    @ViewInject(R.id.jazzyPager)
    private JazzyViewPager jazzyPager;
    @ViewInject(android.R.id.tabhost)
    private TabHost tabHost;

    public static final String TAB_HOME = "0";
    public static final String TAB_BALANCE = "1";
    public static final String TAB_ORDER = "2";
    public static final String TAB_SET = "3";

    List<Fragment> fragments = new ArrayList<>();
    List<Map<String, View>> tabViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
        initJazzyPager(TransitionEffect.Standard);
    }

    private void initView() {
        fragments.add(new HomeFragment());
        fragments.add(new BalanceFragment());
        fragments.add(new OrderFragment());
        fragments.add(new SetFragment());
        
        tabHost.setup();
        tabHost.addTab(tabHost.newTabSpec(TAB_HOME).setIndicator(createTab("收银台", 0)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec(TAB_BALANCE).setIndicator(createTab("查询余额", 1)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec(TAB_ORDER).setIndicator(createTab("交易查询", 2)).setContent(android.R.id.tabcontent));
        tabHost.addTab(tabHost.newTabSpec(TAB_SET).setIndicator(createTab("设置", 3)).setContent(android.R.id.tabcontent));
        // 点击tabHost 来切换不同的消息
        tabHost.setOnTabChangedListener(new OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                int index = Integer.parseInt(tabId);
                setTabSelectedState(index);
                tabHost.getTabContentView().setVisibility(View.GONE);// 隐藏content
            }
        });
        tabHost.setCurrentTabByTag(TAB_HOME);
    }

    /**
     * 动态创建 TabWidget 的Tab项,并设置normalLayout的alpha为1，selectedLayout的alpha为0[显示normal，隐藏selected]
     *
     * @param tabLabelText
     * @param tabIndex
     * @return
     */
    private View createTab(String tabLabelText, int tabIndex) {
        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.main_tabwidget_layout, null);
        TextView normalRadio = (TextView) tabIndicator.findViewById(R.id.normalRadio);
        TextView checkedRadio = (TextView) tabIndicator.findViewById(R.id.checkedRadio);
        Drawable drawable;
        switch (tabIndex) {
            case 0:
                drawable = getResources().getDrawable(R.mipmap.ic_cash_un);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                normalRadio.setCompoundDrawables(null, drawable, null, null);
                drawable = getResources().getDrawable(R.mipmap.ic_cash_on);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                checkedRadio.setCompoundDrawables(null, drawable, null, null);
                break;
            case 1:
                drawable = getResources().getDrawable(R.mipmap.ic_balance_un);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                normalRadio.setCompoundDrawables(null, drawable, null, null);
                drawable = getResources().getDrawable(R.mipmap.ic_balance_on);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                checkedRadio.setCompoundDrawables(null, drawable, null, null);
                break;
            case 2:
                drawable = getResources().getDrawable(R.mipmap.ic_query_un);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                normalRadio.setCompoundDrawables(null, drawable, null, null);
                drawable = getResources().getDrawable(R.mipmap.ic_query_on);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                checkedRadio.setCompoundDrawables(null, drawable, null, null);
                break;
            case 3:
                drawable = getResources().getDrawable(R.mipmap.ic_set_un);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                normalRadio.setCompoundDrawables(null, drawable, null, null);
                drawable = getResources().getDrawable(R.mipmap.ic_set_on);
                drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
                checkedRadio.setCompoundDrawables(null, drawable, null, null);
                break;
            default:
                break;
        }

        normalRadio.setText(tabLabelText);
        checkedRadio.setText(tabLabelText);

        normalRadio.setAlpha(1f);// 透明度显示
        checkedRadio.setAlpha(0f);// 透明的隐藏
        Map<String, View> map = new HashMap<>();
        map.put("normal", normalRadio);
        map.put("selected", checkedRadio);
        tabViews.add(map);
        return tabIndicator;
    }

    /**
     * 设置tab状态选中
     *
     * @param index
     */
    private void setTabSelectedState(int index) {
        for (int i = 0; i < fragments.size(); i++) {
            if (i == index) {
                tabViews.get(i).get("normal").setAlpha(0f);
                tabViews.get(i).get("selected").setAlpha(1f);
            } else {
                tabViews.get(i).get("normal").setAlpha(1f);
                tabViews.get(i).get("selected").setAlpha(0f);
            }
        }
        jazzyPager.setCurrentItem(index, false);// false表示 代码切换 pager 的时候不带scroll动画
    }

    private void initJazzyPager(TransitionEffect effect) {
        jazzyPager.setTransitionEffect(effect);
        FragmentViewPagerAdapter adapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragments);
        jazzyPager.setAdapter(adapter);
        jazzyPager.setFadeEnabled(true);
        jazzyPager.setSlideCallBack(new SlideCallback() {
            @Override
            public void callBack(int position, float positionOffset) {
                Map<String, View> map = tabViews.get(position);
                ViewHelper.setAlpha(map.get("selected"), positionOffset);
                ViewHelper.setAlpha(map.get("normal"), 1 - positionOffset);
            }
        });
        jazzyPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                tabHost.setCurrentTab(position);
            }

            @Override
            public void onPageScrolled(int paramInt1, float paramFloat, int paramInt2) {

            }

            @Override
            public void onPageScrollStateChanged(int paramInt) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setTabSelectedState(tabHost.getCurrentTab());
    }

    class FragmentViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public FragmentViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            jazzyPager.setObjectForPosition(obj, position);
            return obj;
        }

        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        public int getCount() {
            return fragments.size();
        }
    }

}
