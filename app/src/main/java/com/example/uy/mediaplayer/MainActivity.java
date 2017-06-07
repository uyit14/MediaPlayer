package com.example.uy.mediaplayer;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.Annotation;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;

import static android.R.attr.handle;

public class MainActivity extends AppCompatActivity {
    TextView tvTenBaiHat, tvTGBaiHat, tvTGTong;
    ImageButton btnBack, btnPlay, btnStop, btnNext;
    ImageView imgCD;
    SeekBar sbTime;
    ArrayList<BaiHat> arrayList;
    int possition=0;
    MediaPlayer mediaPlayer;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addBaiHat();
        KhoiTao();
        addEvents();
        //
            animation = AnimationUtils.loadAnimation(this, R.anim.xoay);
    }

    //
    private void addEvents(){
        //btnPlay
        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //neu dang phat thi dung va set image la play
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    btnPlay.setImageResource(R.drawable.playx86);
                    imgCD.clearAnimation();
                }
                //neu dang dung thi phat
                else {
                    mediaPlayer.start();
                    btnPlay.setImageResource(R.drawable.pausex86);
                    imgCD.startAnimation(animation);
                }
                TongThoiGian();
                upDateThoiGian();
            }
        });

        //btnStop
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                mediaPlayer.release();
                //set Image for btnPlay
                btnPlay.setImageResource(R.drawable.playx86);
                //Phat lai tu dau
                KhoiTao();
                imgCD.clearAnimation();
            }
        });

        //btnNext
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                possition++;
                if (possition>arrayList.size()-1)
                    possition=0;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                KhoiTao();
                btnPlay.setImageResource(R.drawable.pausex86);
                TongThoiGian();
                upDateThoiGian();
                mediaPlayer.start();
                imgCD.startAnimation(animation);
            }
        });
        //netBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                possition--;
                if (possition<0)
                    possition=arrayList.size()-1;
                if (mediaPlayer.isPlaying())
                    mediaPlayer.stop();
                KhoiTao();
                btnPlay.setImageResource(R.drawable.pausex86);
                TongThoiGian();
                upDateThoiGian();
                mediaPlayer.start();
                imgCD.startAnimation(animation);
            }
        });

        //seekbar
        sbTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            //keo tha ra roi moi thay doi
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });
    }

    //them bai hat
    private void addBaiHat() {
        arrayList = new ArrayList<>();
        arrayList.add(new BaiHat("Ngày Anh Nhớ Em", R.raw.ngay_anh_nho_em));
        arrayList.add(new BaiHat("Ánh Nắng Của Anh", R.raw.anh_nang_cua_anh));
        arrayList.add(new BaiHat("Sương Trắng Miền Quê Ngoại", R.raw.suong_trang_mien_que_ngoai));
        arrayList.add(new BaiHat("Một Mai Giã Từ Vũ Khí", R.raw.gia_tu_vu_khi));
        arrayList.add(new BaiHat("Người Phu Kéo Mo Cau", R.raw.nguoi_phu_keo_mocau));
        arrayList.add(new BaiHat("Lý Cây Bông", R.raw.ly_cay_bong));

    }

    //Anh xa
    private void addControls(){
        tvTenBaiHat = (TextView) findViewById(R.id.tvTenBaiHat);
        tvTGBaiHat = (TextView) findViewById(R.id.tvTGBaiHat);
        tvTGTong = (TextView) findViewById(R.id.tvTGTong);
        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnPlay = (ImageButton) findViewById(R.id.btnPlay);
        btnStop = (ImageButton) findViewById(R.id.btnStop);
        btnNext = (ImageButton) findViewById(R.id.btnNext);
        sbTime = (SeekBar) findViewById(R.id.sbTime);
        imgCD = (ImageView) findViewById(R.id.imgCD);
    }

    //Phat nhat
    private void KhoiTao(){
        mediaPlayer = MediaPlayer.create(MainActivity.this, arrayList.get(possition).getFile());
        tvTenBaiHat.setText(arrayList.get(possition).getTenBaiHat());
    }

    //time bai hat
    private void TongThoiGian(){
        //dinh dang phut:giay
        SimpleDateFormat dinhDang = new SimpleDateFormat("mm:ss");
        //thoi gian tong bai hat
        tvTGTong.setText(dinhDang.format(mediaPlayer.getDuration()));
        //Gan max cua seekbar bang tong thoi gian
        sbTime.setMax(mediaPlayer.getDuration());
    }

    //
    private void upDateThoiGian(){
        final android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDang = new SimpleDateFormat("mm:ss");
                tvTGBaiHat.setText(dinhDang.format(mediaPlayer.getCurrentPosition()));
                //
                sbTime.setProgress(mediaPlayer.getCurrentPosition());
                //Khi bai hat da hoan tat (OnCompletion)
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        possition++;
                        if (possition>arrayList.size()-1)
                            possition=0;
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.stop();
                        KhoiTao();
                        btnPlay.setImageResource(R.drawable.pausex86);
                        TongThoiGian();
                        upDateThoiGian();
                        mediaPlayer.start();
                    }
                });
                handler.postDelayed(this, 500);
            }
        }, 100);
    }



}
