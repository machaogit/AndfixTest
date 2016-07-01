package demo.lanmache.com.testdemo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import demo.lanmache.com.testdemo.fix.A;
import demo.lanmache.com.testdemo.fix.Fix;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "euler";

    private String url = "http://xxxx.xx";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A a = new A(this);

        //此处可以根据版本号判断是否需要启动热修复

        Intent patchIntent = new Intent(this,PatchDownloadService.class);
        patchIntent.putExtra("url",url);
    }


}
