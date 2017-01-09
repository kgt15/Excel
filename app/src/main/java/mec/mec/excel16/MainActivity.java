package mec.mec.excel16;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cleveroad.splittransformation.SquareViewPagerIndicator;
import com.cleveroad.splittransformation.TransformationAdapterWrapper;

/**
 * Created by gopikm on 31/8/16.
 */
public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private SquareViewPagerIndicator indicator;
    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        indicator = (SquareViewPagerIndicator) findViewById(R.id.indicator);
        SimplePagerAdapter adapter = new SimplePagerAdapter(getSupportFragmentManager());
        TransformationAdapterWrapper wrapper = TransformationAdapterWrapper
                .wrap(this, adapter)
                .rows(10)
                .columns(7)
                .marginTop(getResources().getDimensionPixelSize(R.dimen.margin_top))
                .build();
        skip=(Button)findViewById(R.id.skip);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,Home.class);
                startActivity(intent);

            }
        });
        viewPager.setAdapter(wrapper);
        viewPager.setPageTransformer(false, wrapper);
        indicator.initializeWith(viewPager);

    }
    class SimplePagerAdapter extends FragmentStatePagerAdapter {

        private final int[] drawables = new int[] {
//            R.drawable.administrator,
//            R.drawable.cashier,
//            R.drawable.cook,
//            R.drawable.administrator,
//            R.drawable.cashier,
//            R.drawable.cook,
                R.drawable.collegeflat,
                R.drawable.gameintro,
                R.drawable.galeryintro,
                R.drawable.forum
        };

        public SimplePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return PagerFragment.instance(drawables[position]);
        }

        @Override
        public int getCount() {
            return drawables.length;
        }
    }



}
