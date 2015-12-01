package com.clt.ledmanager.app.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.entity.Program;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.FragmentController;
import com.clt.ledmanager.adapter.ExpandableListAdapter;
import com.clt.ledmanager.app.AdvancedActivity;
import com.clt.ledmanager.app.FilesViewActivity;
import com.clt.ledmanager.entity.FileSortModel;
import com.clt.ledmanager.ui.SelectFilePopupWindow;
import com.clt.ledmanager.ui.TitleBarView;
import com.clt.ledmanager.upload.OnUploadListener;
import com.clt.ledmanager.upload.PropertyCommon;
import com.clt.ledmanager.upload.PropertyItem;
import com.clt.ledmanager.upload.PropertyMultiLineText;
import com.clt.ledmanager.upload.PropertyPicture;
import com.clt.ledmanager.upload.PropertyVedio;
import com.clt.ledmanager.upload.UploadManager;
import com.clt.ledmanager.upload.UploadProgram;
import com.clt.ledmanager.upload.UploadTask;
import com.clt.ledmanager.upload.VsnFileFactory;
import com.clt.ledmanager.upload.VsnFileFactoryImpl;
import com.clt.ledmanager.util.Const;
import com.clt.ledmanager.util.FileUtil;
import com.clt.ledmanager.util.SharedPreferenceUtil;
import com.clt.ledmanager.util.SharedPreferenceUtil.ShareKey;
import com.clt.ledmanager.util.Tools;
import com.clt.netmessage.NetMessageType;
import com.mikepenz.materialdrawer.app.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * 上传节目
 */
public class EditProgramFragment extends Fragment {

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

    private static final boolean DBG = true;
    private static final String TAG = "EditProgramFragment";

//    private ArrayList<ExpandableListAdapter.ProgramItem> mSelectFiles = new ArrayList<ExpandableListAdapter.ProgramItem>();

    private FragmentController fragmentController;
    private static final String FRAGMENT_TAG_HOME = "home";

    public ArrayList<Program> programs;


    /**
     * UI控件
     */

    // 标题栏
    private TitleBarView titleBarView;// 标题栏

    // 节目属性
    private EditText etProgramName;// 节目名

    // 窗口属性
    private EditText etStartX, etStartY, etWidth, etHeight;



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

    private String[] arrProgramsNames;

//  新增变量
    private LinearLayout  ulproLayout;
//    private static final String TAG = "UploadProgramFragment";
    private static boolean DEBUG = true;
//    private Button btnUploadProgram;
    private TableRow trUploadProgress;
    private TextView tvUploadProgress;
    private ProgressBar pbUploadProgress;

    private SelectFilePopupWindow selectFilePopupWindow;

    public void toast(String msg, int time)
    {
        Toast.makeText(getActivity(), msg, time).show();
    }
    public String getResString(int resId)
    {
        return getResources().getString(resId);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        ulproLayout  = (LinearLayout)inflater.inflate(R.layout.upload_expandable_list, container, false);

        setHasOptionsMenu(true);
        return ulproLayout;
    }

    private static final int RESULT_NEW_PROGRAM =5001;
    private static final int RESULT_UPLOAD_PROGRAM =5002;
    private static final int RESULT_SAVE_PROGRAM =5003;

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_editprogram, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {

            case R.id.menu_new_program:

                Intent new_program = new Intent(getActivity(),NewProgramFragment.class);
                startActivityForResult(new_program,RESULT_NEW_PROGRAM) ;
                break;

            case R.id.menu_upload_program:

                Intent upload_program = new Intent(getActivity(),TerminalControlFragment.class);
                startActivityForResult(upload_program,RESULT_UPLOAD_PROGRAM) ;
                break;

            case R.id.menu_save_program:

                Intent save_program = new Intent(getActivity(),SaveProgramFragment.class);
                startActivityForResult(save_program,RESULT_SAVE_PROGRAM) ;
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }









    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        init();
//        initView(ulproLayout);
//        initListener();


        init();
        initView();
        initListener();
        initData();
    }

    private void init() {

//		初始化的添加文件的全部数据
        mFileItemList = new ArrayList<>();
        currentSelectedIndex = -1;

        fragmentController = new FragmentController(getActivity());

    }

    private void initView() {

//		增加ExpandableListView的Head和foot
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View headView = inflater.inflate(R.layout.upload_program_head, null);
        View footView = inflater.inflate(R.layout.upload_program_foot, null);

        // title的初始化(上传节目)
        titleBarView = (TitleBarView)ulproLayout.findViewById(R.id.titlebar);//添加了ulproLayout.

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

        trUploadProgress = (TableRow) headView.findViewById(R.id.tr_upload_program);
        tvUploadProgress = (TextView) headView.findViewById(R.id.tv_upload_progress);
        pbUploadProgress = (ProgressBar) trUploadProgress.findViewById(R.id.pb_upload_progress);

//		增加header和footer
        elvItemList = (ExpandableListView)ulproLayout.findViewById(R.id.elv_files);//添加了ulproLayout.
        elvItemList.addHeaderView(headView);
        elvItemList.addFooterView(footView);

//		去掉listView头分割线
        elvItemList.setHeaderDividersEnabled(false);

        adapter = new ExpandableListAdapter(getActivity(), mFileItemList);

        elvItemList.setAdapter(adapter);
    }

    private void initData() {
        etStartX.setText("0");
        etStartY.setText("0");

//		设置窗口宽度
        sharedPreferenceUtil = SharedPreferenceUtil.getInstance(getActivity(), null);
        etWidth.setText(sharedPreferenceUtil.getString(ShareKey.KEY_WIN_WIDTH, "128"));
        etHeight.setText(sharedPreferenceUtil.getString(ShareKey.KEY_WIN_HEIGHT, "128"));
    }

    /**
     * title(上传节目的监听)
     */
    private void initListener() {

        // 标题
/*        titleBarView.setTitleBarListener(new TitleBarListener() {

            @Override
            public void onClickRightImg(View v) {

            }

            @Override
            public void onClickLeftImg(View v) {
                finish();
            }
        });*/


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

                //实例化SelectPicPopupWindow
                selectFilePopupWindow = new SelectFilePopupWindow(getActivity(), itemsOnClick);

                //显示窗口及PopupWindow显示位置
                selectFilePopupWindow.showAtLocation(getActivity().findViewById(R.id.fragment_uploadprogram),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);

            }

            //为弹出窗口实现监听类
            private OnClickListener itemsOnClick = new OnClickListener() {

                public void onClick(View v) {

                    selectFilePopupWindow.dismiss();
                    switch (v.getId()) {
                        case R.id.btn_pick_album:
                            getImageFromAlbum();
                            break;
                        case R.id.btn_pick_other:
                            skipToFileSelectAcitivty(0);
                            break;
                        default:
                            break;
                    }
                }
            };

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

                    Toast.makeText(getActivity(), "亲,没有添加节目哦", Toast.LENGTH_SHORT).show();
                    return;
                }
                UploadProgram uploadProgram = createUploadProgram();
                if (uploadProgram != null) {
                    onUploadPic(uploadProgram);

//                    跳转页面和情况
                    mFileItemList.clear();
                    adapter.updateView(mFileItemList);
                    etProgramName.setText("");

                } else {
                    Toast.makeText(getActivity(), "构建节目失败", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 连接本地相册
     */

    private static final int RESULT_LOAD_IMAGE =1001;

    protected void getImageFromAlbum() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/video");
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
    }

        /**
         * 上传节目
         *
         */
    public void onUploadPic(UploadProgram uploadProgram) {

        if (Application.getInstance().mangerNetService == null || !Application.getInstance().mangerNetService.isConnecting()) {
            Toast.makeText(getActivity(), getResources().getString(R.string.fail_connect_to_server), Toast.LENGTH_LONG).show();
            return;
        }
        if (uploadProgram == null) {
            Toast.makeText(getActivity(), getString(R.string.upload_fail), Toast.LENGTH_LONG).show();
            return;
        }
        UploadAsyncTask task = new UploadAsyncTask(uploadProgram);
        task.execute();
    }


    /**
     * 异步加载节目
     *
     * @author Administrator
     */
    private class UploadAsyncTask extends AsyncTask<Object, Object, Object> {

        private UploadProgram uploadProgram;

        private static final int PROGRESS = 1;

        private boolean uploadSuccess;

        private long totalSize;

        // private long percentSize;

        private Program program;

        private long createTime;

        public UploadAsyncTask(UploadProgram uploadProgram) {
            uploadSuccess = false;
            this.uploadProgram = uploadProgram;
            this.totalSize = uploadProgram.getTotalSize();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            trUploadProgress.setVisibility(View.VISIBLE);
            pbUploadProgress.setProgress(0);
            tvUploadProgress.setText("0");

        }

        @Override
        protected void onProgressUpdate(Object... values) {
            super.onProgressUpdate(values);
            int flag = (Integer) values[0];
            if (flag == PROGRESS) {
                long precentSize = (Long) values[1];
                //long totalSize = (Long) values[2];
                pbUploadProgress.setProgress((int) (precentSize * 100 / totalSize));
                String progressStr = Tools.byte2KbOrMb(precentSize) + "/" + Tools.byte2KbOrMb(totalSize);
                tvUploadProgress.setText((int) (precentSize * 100 / totalSize) + "%");

            }
        }

        @Override
        protected void onPostExecute(Object result) {
            super.onPostExecute(result);
            trUploadProgress.setVisibility(View.GONE);
            if (uploadSuccess) {
                Toast.makeText(getActivity(), getString(R.string.upload_sucessful), Toast.LENGTH_LONG).show();
                if (program != null) {
                    Application.getInstance().programs.add(program);
                    Application.getInstance().mangerNetService.setPlayProgram(program);

                    Message msg = new Message();
                    msg.what = NetMessageType.MSG_WHAT_PROGRAM_UPDATE;
                    AdvancedActivity.MessageWrapper messageWrapper = new AdvancedActivity.MessageWrapper(AdvancedActivity.MessageWrapper.TYPE_SERVICE_UPDATE, msg);
                    Application.getInstance().terminateObservable.dealHandlerMessage(messageWrapper);

                }
            } else {
                Toast.makeText(getActivity(), getString(R.string.upload_fail), Toast.LENGTH_LONG).show();
            }
        }

//        上传图片的核心逻辑
        @Override
        protected Object doInBackground(Object... params) {
            UploadManager uploadManager = new UploadManager(uploadProgram);
            uploadSuccess = uploadManager.executeUpload(
                    Application.getInstance().ledTerminateInfo.getIpAddress(), new OnUploadListener() {
                        @Override
                        public void onUploadprogress(long percentSize, long totalSize) {publishProgress(new Object[]{PROGRESS, percentSize, totalSize});
                        }
                    });
            if (!uploadSuccess) {
                return null;
            }
            program = new Program();
            program.setFileName(uploadProgram.getVsnFileTask().getLocalFile().getName());
            program.setPath("/mnt/sdcard/Android/data/com.color.home/files/Ftp/program");
            program.setPathType(Program.SDCARD);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            program.setCreateTime(format.format(createTime));

            // /**
            // * 3.删除本地节目文件
            // */

            FileUtil.deleteLocalFile(uploadProgram.getVsnFileTask().getLocalFile());
            return null;
        }
    }

    /**
     * 获得节目名称   如 new.vsn->new
     *
     * @param name
     * @return
     */
    private String getProgramName(String name) {
        if (!TextUtils.isEmpty(name)) {
            if (name.endsWith(".vsn") || name.endsWith(".VSN")) {
                return name.substring(0, name.lastIndexOf("."));
            }
        }
        return null;
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
                    ((PropertyPicture) p).pictureName = new File(programItem.filePath).getName();
                    ((PropertyPicture) p).fsFilePath = "./" + this.programName + ".files/" + ((PropertyPicture) p).pictureName;
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

        {for (int i = 0; i < mFileItemList.size(); i++)
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
        mFileItemList.addAll(programItems);//把选好的节目都添加到mFileItemList


        adapter.updateView(mFileItemList);
        elvItemList.setHeaderDividersEnabled(true);
        elvItemList.setAdapter(adapter);

        getFilesProperty(mFileItemList);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

            if(resultCode != Activity.RESULT_OK){
                return;
            }
                if (requestCode == 0) {
                    // 一个或多个
                    ArrayList<FileSortModel> list = (ArrayList<FileSortModel>) data.getSerializableExtra("selectedFiles");
                    ExpandableListAdapter.ProgramItem proItem = null;

                    for (int i = 0; i < list.size(); i++) {
                        proItem = new ExpandableListAdapter.ProgramItem();
                        FileSortModel fsm = list.get(i);
                        proItem.fileName = fsm.getFileName();
                        proItem.filePath = fsm.getFilePath();
                        proItem.fileType = fsm.getFileType();
                        switch (proItem.fileType) {
                            case Const.PICTURE:
                                proItem.property = new PropertyPicture();
                                break;

                            case Const.VIDEO:
                                proItem.property = new PropertyVedio();
                                break;

                            case Const.TXT:
                                proItem.property = new PropertyMultiLineText();
                                break;
                        }
                        mFileItemList.add(proItem);
                    }
                    onSelectProgramItems(mFileItemList);

                }else if (requestCode == RESULT_LOAD_IMAGE ){

                    ExpandableListAdapter.ProgramItem proItem = new ExpandableListAdapter.ProgramItem();

                    if (DBG) {
                        Log.d(TAG, "getData = " +data.getData());
                    }

                        Uri selectedFile = data.getData();
                        String [] filePathColumn = { MediaStore.Images.Media.DATA };
                        Cursor cursor = getActivity().getContentResolver().query(selectedFile, filePathColumn, null, null, null);
                        cursor.moveToFirst();
                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String filePath = cursor.getString(columnIndex);
                        cursor.close();

                        if (DBG) {
                         Log.d(TAG, "picturePath = " + filePath);
                        }

//                    图片/视频属性设置
                        File file = new File(filePath);
                        proItem.fileName = file.getName();
                        proItem.filePath = filePath;

                    if(file.getName().endsWith(".jpg") || file.getName().endsWith(".png")){

                        proItem.fileType = Const.PICTURE;
                        proItem.property = new PropertyPicture();

                    }else if(file.getName().endsWith(".3gp") || file.getName().endsWith(".mp4")
                            || file.getName().endsWith(".rm") || file.getName().endsWith(".avi")
                            || file.getName().endsWith(".rmvb") || file.getName().endsWith(".wmv")) {

                        proItem.fileType = Const.VIDEO;
                        proItem.property = new PropertyVedio();
                    }
                        mFileItemList.add(proItem);
                        onSelectProgramItems(mFileItemList);
                    }
            }

    /**
     * 异步获取文件的属性
     */
    private class AsyncGetFilePropertyTask extends AsyncTask<Object, Object, Object> {
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
                    getImageProperty((PropertyPicture) pt.property, pt.filePath);
                    break;
                case Const.VIDEO:
                    getVedioProperty((PropertyVedio) pt.property, pt.filePath);
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
            e.printStackTrace();
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
            MediaPlayer mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(filePath));//修改了this,变成getActivity()
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
        Intent intent = new
                Intent(getActivity(), FilesViewActivity.class);//修改了getActivity()
        intent.putExtra("fileType", fileType);
        startActivityForResult(intent, 0);//修改了getActivity()
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

        }else{

            if (Application.getInstance().programs == null){
                    return false;

            }/*else {
                int size = Application.getInstance().programs.size();
                Program program = null;
                for (int i = 0; i < size; i++){
                    program = Application.getInstance().programs.get(i);
                    if (program.getFileName().equals(etProgramName.getText().toString().trim())){

                        etProgramName.setText("");
                        Toast.makeText(getActivity(), "亲,您输入的节目名称,已经存在了哦,需要重新输入哦", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                }
            }*/
        }
        return true;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFileItemList.clear();
    }
}
