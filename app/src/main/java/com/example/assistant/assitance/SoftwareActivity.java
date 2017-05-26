package com.example.assistant.assitance;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;

import com.example.assistant.assitance.com.example.assitance.adapter.GirdViewAdapter;
import com.example.assistant.assitance.com.example.assitance.adapter.ListViewAdapter;
import com.example.assistant.assitance.com.example.assitance.enety.AppInfo;
import com.example.assistant.assitance.com.example.assitance.util.AppInfoWrapped;

import java.util.List;

/**
 * 软件管理
 */
public class SoftwareActivity extends BaseActivity {
    private GridView mGid_sys;
    private ListView mLst_sys;
    private GridView mGid_user;
    private ListView mLst_user;
    private List<AppInfo> appInfoAllSys;
    private List<AppInfo> appInfoAllUser;
    private TabHost mTab;
    private TabHost.TabSpec mTsc_sys;
    private TabHost.TabSpec mTsc_user;
    private ImageButton mIbt_grid_soft;
    private ImageButton mIbt_list_soft;
    private AppInfoWrapped appInfoWrapped;
    private TextView mTxt;
    private ListViewAdapter listSysAdapter;
    private GirdViewAdapter girdSysAdapter;
    private ListViewAdapter listUserAdapter;
    private GirdViewAdapter girdUserAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_software);
        initView();//初始化
        setTabHost();//设置TabHost界面
        getData();//获取数据源
        setMyAdapter();//设置适配器
        setImagButtonClick();//给按钮设置点击事件
        getDialog();//设置子选项的点击事件
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    //初始化
    public void initView() {
        mLst_sys = (ListView) this.findViewById(R.id.lst_sys);//SYS List
        mLst_sys.setVisibility(View.GONE);
        mGid_sys = (GridView) this.findViewById(R.id.gid_sys);//SYS GRID
        mLst_user = (ListView) this.findViewById(R.id.lst_user);//USER List
        mLst_user.setVisibility(View.GONE);
        mGid_user = (GridView) this.findViewById(R.id.gid_user);//USER GRID
        mTab = (TabHost) this.findViewById(R.id.tab);
        mTab.setup();
        mIbt_grid_soft = (ImageButton) this.findViewById(R.id.ibt_grid_soft);
        mIbt_list_soft = (ImageButton) this.findViewById(R.id.ibt_list_soft);
        mIbt_list_soft.setVisibility(View.GONE);
        mTxt = (TextView)this.findViewById(R.id.txt_page);
    }


    //设置TabHost
    public void setTabHost() {
        mTsc_sys = mTab.newTabSpec("system");
        mTsc_sys.setIndicator(this.createView(R.mipmap.sys_app));
        mTsc_sys.setContent(R.id.content1);
        mTab.addTab(mTsc_sys);

        mTsc_user = mTab.newTabSpec("user");
        mTsc_user.setIndicator(this.createView(R.mipmap.user_app));
        mTsc_user.setContent(R.id.content2);
        mTab.addTab(mTsc_user);
    }


    //获取数据源
    public void getData(){
        appInfoWrapped = new AppInfoWrapped(this);
        appInfoAllSys =appInfoWrapped.sysAppInfo();//获取系统的信息
        appInfoAllUser = appInfoWrapped.userAppInfo();//获取用户的信息
    }


    //设置适配器
    public void setMyAdapter(){

        listSysAdapter = new ListViewAdapter(this,appInfoAllSys);
        girdSysAdapter = new GirdViewAdapter(this,appInfoAllSys);
        listUserAdapter = new ListViewAdapter(this,appInfoAllUser);
        girdUserAdapter = new GirdViewAdapter(this, appInfoAllUser);

        mLst_sys.setAdapter(listSysAdapter);//给系统的ListView设置适配器
        mGid_sys.setAdapter(girdSysAdapter);//给系统的GridView设置适配器
        mLst_user.setAdapter(listUserAdapter);//给用户的ListView设置适配器
        mGid_user.setAdapter(girdUserAdapter);//给用户的GridView设置适配器
    }


    //给按钮设置点击事件
   public void setImagButtonClick() {
       mIbt_grid_soft.setOnClickListener(new ButtonClick());//给Grid的按钮设置点击事件
       mIbt_list_soft.setOnClickListener(new ButtonClick());//给List的按钮设置点击事件
    }


    //设置子选项的点击事件
    public void getDialog(){
        mLst_sys.setOnItemClickListener(new SysLst());//给系统的ListView的子选项设置点击事件
        mLst_user.setOnItemClickListener(new UserLst());//给用户的ListView的子选项设置点击事件
        mGid_sys.setOnItemClickListener(new SysGid());//给系统的GridView的子选项设置点击事件
        mGid_user.setOnItemClickListener(new UserGid());//给用户的GridView的子选项设置点击事件
    }



    //设置标签的图片
    public View createView(int resId) {
        View view = LayoutInflater.from(this).inflate(R.layout.software_tag, null);
        ImageView img = (ImageView) view.findViewById(R.id.soft_img);
        img.setImageResource(resId);
        return view;
    }


    //按钮的点击事件
    class ButtonClick implements View.OnClickListener{

    @Override
        public void onClick(View v) {
             switch (v.getId()){
                 case R.id.ibt_grid_soft://当点击GridView的按钮时
                    mIbt_grid_soft.setVisibility(View.GONE);//隐藏GridView的按钮
                    mIbt_list_soft.setVisibility(View.VISIBLE);//显示ListView的按钮
                    mLst_sys.setVisibility(View.VISIBLE);
                    mLst_user.setVisibility(View.VISIBLE);
                    mGid_sys.setVisibility(View.GONE);
                    mGid_user.setVisibility(View.GONE);
                    break;
                 case R.id.ibt_list_soft://当点击ListView的按钮时

                    mIbt_grid_soft.setVisibility(View.VISIBLE);//显示GridView的按钮
                    mIbt_list_soft.setVisibility(View.GONE);//隐藏ListView的按钮
                    mLst_sys.setVisibility(View.GONE);
                    mLst_user.setVisibility(View.GONE);
                    mGid_sys.setVisibility(View.VISIBLE);
                    mGid_user.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }


    //mLst_sys的点击事件
    class SysLst implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            SoftwareActivity.this.ItemClick(appInfoAllSys.get(position));
        }
    }


    //mLst_user的点击事件
    class UserLst implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SoftwareActivity.this.ItemClick(appInfoAllUser.get(position));
        }
    }


    //mGid_sys的点击事件
    class SysGid implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            SoftwareActivity.this.ItemClick(appInfoAllSys.get(position));
        }
    }


    //mGid_user的点击事件
    class UserGid implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            SoftwareActivity.this.ItemClick(appInfoAllUser.get(position));
        }
    }


    //设置子选项的对话框
    public void ItemClick(AppInfo appInfos){
        final AppInfo appInfo;
        appInfo=appInfos;
        //对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(SoftwareActivity.this);
        builder.setTitle("选项");
        String[] item = {"详细信息", "卸载程序"};
        builder.setItems(item, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        AlertDialog.Builder builder_mag = new AlertDialog.Builder(SoftwareActivity.this);
                        builder_mag.setTitle("详细信息");

                        builder_mag.setIcon(appInfo.getmIcon());
                        builder_mag.setMessage("" + "程序名称:" + appInfo.getmLabel() + "\n" +
                                "程序包名:" + appInfo.getmPackageName() + "\n" +
                                "版本编号:" + appInfo.getmVersion() + "\n" +
                                "版本名称:" + appInfo.getmVName());
                        builder_mag.show();
                        break;
                    case 1:
                        Intent delete = new Intent(Intent.ACTION_DELETE);
                        delete.setData(Uri.parse("package:"+appInfo.getmPackageName()));
                        SoftwareActivity.this.startActivityForResult(delete,1);//点触发系统，再这里可以进行数据更新操作
                        break;
                }
            }
        });
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            AppInfoWrapped appInfoWrappedUpdata = new AppInfoWrapped();//重新new封装好的对象
            appInfoAllSys = appInfoWrappedUpdata.sysAppInfo();//重新获取系统的信息
            appInfoAllUser = appInfoWrappedUpdata.userAppInfo();//重新获取用户的信息
            listSysAdapter.updateData(appInfoAllSys);//更新系统ListView中的数据
            girdSysAdapter.updateData(appInfoAllSys);//更新系统GridView中的数据
            listUserAdapter.updateData(appInfoAllUser);//更新用户ListView中的数据
            girdUserAdapter.updateData(appInfoAllUser);//更新用户GridView中的数据
        }
    }

}
