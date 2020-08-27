package com.kkr95.clickclick;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout ll;
    TextView tv;
    ImageView[] ivs= new ImageView[12];
    ImageView iv;

    AdView adView;
    InterstitialAd interstitialAd;

    int cnt= 0;
    int arr[]= new int[12];
    Random rnd= new Random();
    int stage= 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adView= findViewById(R.id.adView);
        AdRequest adRequest= new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        initial();

        ll= findViewById(R.id.ll);
        tv= findViewById(R.id.tv);
        iv= findViewById(R.id.iv_start);
        for(int i=0; i<ivs.length; i++){
            ivs[i]= findViewById(R.id.iv01+i);
            ivs[i].setImageResource(R.drawable.num01+arr[i]);
            ivs[i].setTag(arr[i]);
        }

    }//onCreate method

    void initial(){
        for(int i=0; i<12; i++){
            arr[i]= rnd.nextInt(12);
            for(int k=0; k<i; k++){
                if(arr[i]==arr[k]){
                    i--;
                    break;
                }
            }
        }

    }

    public void clickStart(View v){
        ImageView iv= (ImageView)v;
        iv.setImageResource(R.drawable.ing);

        for(int i=0; i<ivs.length; i++){
            ivs[i].setVisibility(View.VISIBLE);
        }
        tv.setText("숫자를 순서대로 누르세요");
    }

    public void clickIv(View v){
        String s= v.getTag().toString();
        int num= Integer.parseInt(s);

        if(num==cnt){
            v.setVisibility(View.INVISIBLE);
            cnt++;

            if(cnt>=12){
                cnt= 0;
                clearStage();
            }
        }
    }////

    void clearStage(){
        interstitialAd= new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-3016192843131951/5231991948");
        interstitialAd.loadAd(new AdRequest.Builder().build());
        interstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                interstitialAd.show();
            }
        });

        stage++;
        initial();

        for(int i=0; i<12; i++) ivs[i].setTag(arr[i]);

        if(stage==2){
            for(int i=0; i<12; i++){
                ivs[i].setVisibility(View.VISIBLE);
                ivs[i].setImageResource(R.drawable.alpa01+arr[i]);
                tv.setText("알파벳을 순서대로 누르세요");
                ll.setBackgroundResource(R.drawable.bg2);
            }
        }
        if(stage==3){
            for(int i=0; i<12; i++){
                ivs[i].setVisibility(View.VISIBLE);
                ivs[i].setImageResource(R.drawable.cha01+arr[i]);
                tv.setText("십이지신을 순서대로 누르세요");
                ll.setBackgroundResource(R.drawable.bg3);
            }
        }
        if(stage==4){
            tv.setText("");
            iv.setVisibility(View.INVISIBLE);
            ll.setBackgroundResource(R.drawable.bg4);
        }

    }

}
