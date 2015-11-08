package com.clt.ledmanager.app;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.clt.ledmanager.adapter.ExpandableListAdapter;
import com.clt.ledmanager.entity.FileSortModel;
import com.clt.ledmanager.ui.TitleBarView;
import com.clt.ledmanager.ui.TitleBarView.TitleBarListener;
import com.clt.ledmanager.upload.PropertyCommon;
import com.clt.ledmanager.upload.PropertyItem;
import com.clt.ledmanager.upload.PropertyMultiLineText;
import com.clt.ledmanager.upload.PropertyPicture;
import com.clt.ledmanager.upload.PropertyVedio;
import com.clt.ledmanager.upload.UploadProgram;
import com.clt.ledmanager.upload.UploadTask;
import com.clt.ledmanager.upload.VsnFileFactory;
import com.clt.ledmanager.upload.VsnFileFactoryImpl;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.SharedPreferenceUtil;
import com.clt.ledmanager.util.SharedPreferenceUtil.ShareKey;
import com.mikepenz.materialdrawer.app.R;

import java.io.File;
import java.util.ArrayList;


/**
 * 上传节目
 */
public class UploadProgramAcitvity extends com.clt.ledmanager.activity.BaseActivity {

    // 字体
    public static final String[] fontFamily = {"宋体", "黑体", "楷体", "隶书", "仿宋"};

    // 字体大小
    public static final String[] fontSize = {"8", "9", "10", "11", "12", "14",
            "16", "18", "20", "22", "24", "26", "28", "36", "48", "72", "128"};

    // 文字颜色
    public static final int[] textColorArr = {Color.BLACK, Color.DKGRAY,
            Color.GRAY, Color.LTGRAY, Color.WHITE, Color.RED, Color.GREEN,
            Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA

    };

    private ArrayList<ExpandableListAdapter.ProgramItem> mSelectFiles = new ArrayList<>();

    /**
     * UI控件
     */

    // 标题栏
    private TitleBarView titleBarView;// 标题栏

    // 节目属性
    private EditText etProgramName;// 节目名

    // 窗口属性
    private EditText etStartX, etStartY, etWidth, etHeight;


    // 添加上传节目的ListView和数据Adapter
    private ExpandableListView elvItemList;
    private ExpandableListAdapter adapter;

    private int currentSelectedIndex;

    private String[] arrFileNames;

    //	添加文件按钮
    private Button btnAddFile;
    private LinearLayout llInclude;

    //	上传节目按钮
    private Button btnCreateAndUpload;

    /**
     * 变量
     */

    private String programName;// 节目名

    private View cachePicView, cacheVedioView, cacheTxtVideo;


    // 素材列表
    private ArrayList<ExpandableListAdapter.ProgramItem> mFileItemList;
    private AsyncGetFilePropertyTask asyncGetFilePropertyTask;

    //全局属性
    private PropertyCommon propertyCommon;
    private SharedPreferenceUtil sharedPreferenceUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.upload_expandable_list);
            init();
            initView();
            initListener();
            initData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void init() {

//		初始化的添加文件的全部数据
        mFileItemList = new ArrayList<>();
        currentSelectedIndex = -1;

    }

    private void initView() {
//		增加ExpandableListView的Head和foot
        LayoutInflater inflater = LayoutInflater.from(this);
        View headView = inflater.inflate(R.layout.upload_program_head, null);
        View footView = inflater.inflate(R.layout.upload_program_foot, null);

        // title的初始化(上传节目)
        titleBarView = (TitleBarView) findViewById(R.id.titlebar);

        // 节目名(上传节目)
        etProgramName = (EditText) headView.findViewById(R.id.et_program_name);

        // 窗口属性
        etStartX = (EditText) headView.findViewById(R.id.et_start_x);
        etStartY = (EditText) headView.findViewById(R.id.et_start_y);
        etWidth = (EditText) headView.findViewById(R.id.et_program_width);
        etHeight = (EditText) headView.findViewById(R.id.et_program_height);

//		llInclude = (LinearLayout) findViewById(R.id.ll_include);
//		spinnerFileNameList = (CustomerSpinner) findViewById(R.id.spinner_file_name);

        // 添加文件Button
        btnAddFile = (Button) headView.findViewById(R.id.btn_select_file);

        // 上传节目按钮
        btnCreateAndUpload = (Button) footView.findViewById(R.id.btn_creat_upload);

//		增加header和footer
        elvItemList = (ExpandableListView) findViewById(R.id.elv_files);
        elvItemList.addHeaderView(headView);
        elvItemList.addFooterView(footView);

//		去掉listView头分割线
        elvItemList.setHeaderDividersEnabled(false);

        adapter = new ExpandableListAdapter(this, mFileItemList);

        elvItemList.setAdapter(adapter);
    }

    private void initData() {
        etStartX.setText("0");
        etStartY.setText("0");

//		设置窗口宽度
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(this, null);
        etWidth.setText(sharedPreferenceUtil.getString(ShareKey.KEY_WIN_WIDTH, "128"));
        etHeight.setText(sharedPreferenceUtil.getString(ShareKey.KEY_WIN_HEIGHT, "128"));
    }

    /**
     * title(上传节目的监听)
     */
    private void initListener() {
        // 标题
        titleBarView.setTitleBarListener(new TitleBarListener() {

            @Override
            public void onClickRightImg(View v) {

            }

            @Override
            public void onClickLeftImg(View v) {
                finish();
            }
        });


//		上传宽度的监听
        etWidth.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            //			保存修改后窗口宽
            @Override
            public void afterTextChanged(Editable s) {
                String w = s.toString().trim();
                if (!TextUtils.isEmpty(w)) {
                    int num = Integer.parseInt(w);
                    sharedPreferenceUtil.putString(ShareKey.KEY_WIN_WIDTH, w);
                }
            }
        });

//		保存修改后窗口高
        etHeight.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // TODO Auto-generated method stub

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable s) {
                String h = s.toString().trim();
                if (!TextUtils.isEmpty(h)) {
                    int num = Integer.parseInt(h);
                    sharedPreferenceUtil.putString(ShareKey.KEY_WIN_HEIGHT, h);
                }
            }
        });

        /**
         * 可扩展列表,ListViewItem的监听
         */
        elvItemList.setOnItemClickListener(new OnItemClickListener() {
            //			设置可扩展的列表监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                for (int i = 0; i < mFileItemList.size(); i++) {
                    if (i == position) {
                        elvItemList.expandGroup(i);
                    } else {
                        elvItemList.collapseGroup(i);
                    }
                }
            }
        });

        /**
         * 添加文件
         */
        btnAddFile.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
//				??参数
                skipToFileSelectAcitivty(0);
            }
        });

        /**
         * 创建并上传节目
         */
        btnCreateAndUpload.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!doFilter()) {
                    return;
                }
                if (mFileItemList == null || mFileItemList.size() == 0) {
                    return;
                }
                UploadProgram uploadProgram = createUploadProgram();
                if (uploadProgram != null) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "uploadProgram");
                    intent.putExtra("ProgramInfo", uploadProgram);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                } else {
                    Toast.makeText(UploadProgramAcitvity.this, "构建节目失败", Toast.LENGTH_SHORT);
                }
//					Intent intent = new Intent(UploadProgramAcitvity.this,
//							MyTabActivity.class);
//					intent.putExtra("Type", "uploadProgram");
//					intent.putExtra("ProgramInfo", uploadProgram);
//					startActivity(intent);
//					finish();

            }

        });
    }

    /**
     * 构建上传节目
     *
     * @return
     */
    private UploadProgram createUploadProgram() {
        int width = Integer.parseInt(etWidth.getText().toString().trim());
        int height = Integer.parseInt(etHeight.getText().toString().trim());
        int startX = Integer.parseInt(etStartX.getText().toString().trim());
        int startY = Integer.parseInt(etStartY.getText().toString().trim());
        this.programName = etProgramName.getText().toString().trim();

        //共有属性
        propertyCommon.infoWidth = String.valueOf(width);
        propertyCommon.infoHeight = String.valueOf(height);
        propertyCommon.rectWidth = propertyCommon.infoWidth;
        propertyCommon.rectHeight = propertyCommon.infoHeight;
        propertyCommon.rectX = String.valueOf(startX);
        propertyCommon.rectY = String.valueOf(startY);
        propertyCommon.programName = programName;


        ExpandableListAdapter.ProgramItem programItem = null;
        PropertyItem p = null;
        //配置素材属性
        for (int i = 0; i < mFileItemList.size(); i++) {
            programItem = mFileItemList.get(i);
            p = programItem.property;

            switch (programItem.fileType) {
                case Const.PICTURE: {
                    ((PropertyPicture) p).pictureName = new File(
                            programItem.filePath).getName();
                    ((PropertyPicture) p).fsFilePath = "./" + this.programName
                            + ".files/" + ((PropertyPicture) p).pictureName;
                    p.materialName = ((PropertyPicture) p).pictureName;
                }
                break;
                case Const.VIDEO: {
                    ((PropertyVedio) p).vedioName = new File(programItem.filePath).getName();
                    ((PropertyVedio) p).fsFilePath = "./" + this.programName + ".files/" + ((PropertyVedio) p).vedioName;
                    p.materialName = ((PropertyVedio) p).vedioName;
                }
                break;
                case Const.TXT: {
                    ((PropertyMultiLineText) p).fileName = new File(programItem.filePath).getName();
                    ((PropertyMultiLineText) p).fsFilePath = "./" + this.programName + ".files/" + ((PropertyMultiLineText) p).fileName;
                    p.materialName = ((PropertyMultiLineText) p).fileName;
                }
                break;
            }

        }

        UploadProgram uploadProgram = new UploadProgram();
        // 创建vsn文件
        VsnFileFactory factory = new VsnFileFactoryImpl();
        String vsnPath = "/mnt/sdcard/" + this.programName + ".vsn";

        ArrayList<PropertyItem> ptlist = new ArrayList<PropertyItem>();
        for (int i = 0; i < mFileItemList.size(); i++) {
            ptlist.add(mFileItemList.get(i).property);
        }

        File vsnFile = factory.createVsnFile(propertyCommon, ptlist, vsnPath);
        if (vsnFile == null) {
            return null;
        }
        uploadProgram.setVsnFileTask(new UploadTask(vsnFile, "/program/"));
        // 构建上传节目
        String remoteDir = "/program/" + this.programName + ".files/";
        uploadProgram.setRemoteDirtory(remoteDir);

        String filePath = null;
        String fileName = null;
        for (int i = 0; i < mFileItemList.size(); i++) {
            filePath = mFileItemList.get(i).filePath;
            fileName = mFileItemList.get(i).fileName;
            uploadProgram.getFileTaskList().add(new UploadTask(new File(filePath), remoteDir + fileName));
        }
        return uploadProgram;

    }


    /**
     * 选择了节目素材之后回调
     *
     * @param programItems
     */
    public void onSelectProgramItems(ArrayList<ExpandableListAdapter.ProgramItem> programItems) {
        if (programItems == null || programItems.size() == 0) {
            return;
        }
        propertyCommon = new PropertyCommon();


        mFileItemList = new ArrayList<>();
        mFileItemList.addAll(programItems);


        adapter.updateView(mFileItemList);
        elvItemList.setHeaderDividersEnabled(true);
        elvItemList.setAdapter(adapter);

        getFilesProperty(mFileItemList);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == 0 && resultCode == Activity.RESULT_OK) {
                // 一个或多个
                ArrayList<FileSortModel> list = (ArrayList<FileSortModel>) data.getSerializableExtra("selectedFiles");
                ExpandableListAdapter.ProgramItem pt = null;
                FileSortModel fsm = null;
                for (int i = 0; i < list.size(); i++) {
                    pt = new ExpandableListAdapter.ProgramItem();
                    fsm = list.get(i);
                    pt.fileName = fsm.getFileName();
                    pt.filePath = fsm.getFilePath();
                    pt.fileType = fsm.getFileType();
                    switch (pt.fileType) {
                        case Const.PICTURE:
                            pt.property = new PropertyPicture();
                            break;

                        case Const.VIDEO:
                            pt.property = new PropertyVedio();
                            break;
                        case Const.TXT:
                            pt.property = new PropertyMultiLineText();
                            break;
                    }
                    mSelectFiles.add(pt);
                }
                onSelectProgramItems(mSelectFiles);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 异步获取文件的属性
     */
    private class AsyncGetFilePropertyTask extends
            AsyncTask<Object, Object, Object> {
        private boolean isFinish;

        private ArrayList<ExpandableListAdapter.ProgramItem> selectFiles;

        public AsyncGetFilePropertyTask(ArrayList<ExpandableListAdapter.ProgramItem> selectFiles) {
            this.selectFiles = selectFiles;
            isFinish = false;
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            isFinish = true;
        }

        public boolean isFinish() {
            return isFinish;
        }

        @Override
        protected Object doInBackground(Object... params) {
            getFilesProperty(selectFiles);
            return null;
        }


    }

    /**
     * 获得文件的属性
     */
    private void getFilesProperty(ArrayList<ExpandableListAdapter.ProgramItem> selectFiles) {
        int size = selectFiles.size();
        int type = -1;
        ExpandableListAdapter.ProgramItem pt = null;
        for (int i = 0; i < size; i++) {
            pt = selectFiles.get(i);
            switch (pt.fileType) {
                case Const.PICTURE:
                    getImageProperty((PropertyPicture) pt.property,
                            pt.filePath);
                    break;
                case Const.VIDEO:
                    getVedioProperty((PropertyVedio) pt.property,
                            pt.filePath);
                    break;
            }
        }
    }

    private void getImageProperty(PropertyPicture p, String filePath) {
        try {
            Bitmap bf = BitmapFactory.decodeFile(filePath);
            p.pictureWidth = String.valueOf(bf.getWidth());
            p.pictureHeight = String.valueOf(bf.getHeight());
        } catch (Exception e) {
            return;
        }
    }

    private void getVedioProperty(PropertyVedio p, String filePath) {
        try {
            String vedioWidth = null, vedioHeight = null, vedioDuration = null;
            // 获得视频的元数据， 两种方式解析
            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(filePath);
            String width = retriever.extractMetadata(18);
            String height = retriever.extractMetadata(19);
            String duration = retriever
                    .extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            retriever.release();
            if (width != null) {
                vedioWidth = width;
            }
            if (height != null) {
                vedioHeight = height;
            }

            if (duration != null) {
                vedioDuration = duration;
            }
            MediaPlayer mediaPlayer = MediaPlayer.create(this,
                    Uri.parse(filePath));
            if (mediaPlayer != null) {
                if (vedioWidth == null) {
                    vedioWidth = String.valueOf(mediaPlayer.getVideoWidth());
                }
                if (vedioHeight == null) {
                    vedioHeight = String.valueOf(mediaPlayer.getVideoHeight());
                }
                if (vedioDuration == null) {
                    vedioDuration = String.valueOf(mediaPlayer.getDuration());
                }
            }

            if (vedioWidth == null) {
                vedioWidth = sharedPreferenceUtil.getString(ShareKey.KEY_WIN_WIDTH, "128");
            }
            if (vedioHeight == null) {
                vedioHeight = sharedPreferenceUtil.getString(ShareKey.KEY_WIN_HEIGHT, "128");
            }
            p.vedioWidth = String.valueOf(vedioWidth);
            p.vedioHeight = String.valueOf(vedioHeight);
            p.showWidth = p.vedioWidth;
            p.showHeight = p.vedioHeight;
            p.length = String.valueOf(vedioDuration);
            p.playLength = ((PropertyVedio) p).length;
            p.materialDuration = ((PropertyVedio) p).length;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据文件类型获得不同的参数
     *
     * @param fileType
     * @return
     */
    public PropertyItem getFilePropertyByType(int fileType) {
        if (fileType == Const.PICTURE) {
            return new PropertyPicture();

        } else if (fileType == Const.VIDEO) {
            return new PropertyVedio();
        } else if (fileType == Const.TXT) {
            return new PropertyMultiLineText();
        }
        return null;
    }

    /**
     * 跳转到选择文件的页面
     */
    public void skipToFileSelectAcitivty(int fileType) {
        Intent intent = new Intent(UploadProgramAcitvity.this,
                FilesViewActivity.class);
        intent.putExtra("fileType", fileType);
        UploadProgramAcitvity.this.startActivityForResult(intent, 0);
    }

    /**
     * 过滤条件
     */
    protected boolean doFilter() {
        // 宽高检测
        int width = 0, height = 0;
        int startX = 0, startY = 0;
        try {
            width = Integer.parseInt(etWidth.getText().toString().trim());
        } catch (NumberFormatException e) {
            toast(getResString(R.string.please_input_int), Toast.LENGTH_SHORT);
            etWidth.requestFocus();
            return false;
        }

        try {
            height = Integer.parseInt(etHeight.getText().toString().trim());
        } catch (NumberFormatException e) {
            toast(getResString(R.string.please_input_int), Toast.LENGTH_SHORT);
            etHeight.requestFocus();
            return false;
        }
        try {
            startX = Integer.parseInt(etStartX.getText().toString().trim());
        } catch (NumberFormatException e) {
            toast(getResString(R.string.please_input_int), Toast.LENGTH_SHORT);
            etStartX.requestFocus();
            return false;
        }

        try {
            startY = Integer.parseInt(etStartY.getText().toString().trim());
        } catch (NumberFormatException e) {
            toast(getResString(R.string.please_input_int), Toast.LENGTH_SHORT);
            etStartY.requestFocus();
            return false;
        }

        // 节目名检测
        if (TextUtils.isEmpty(etProgramName.getText().toString().trim())) {
            toast(getResString(R.string.please_input_program_name), Toast.LENGTH_SHORT);
            etProgramName.requestFocus();
            return false;
        }

        return true;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFileItemList.clear();
    }
}
