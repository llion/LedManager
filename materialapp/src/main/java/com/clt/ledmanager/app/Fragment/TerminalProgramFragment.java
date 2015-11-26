package com.clt.ledmanager.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.clt.entity.Program;
import com.clt.ledmanager.activity.Application;
import com.clt.ledmanager.activity.BaseObserverFragment;
import com.clt.ledmanager.adapter.SpinnerAdapter;
import com.clt.ledmanager.ui.DialogFactory;
import com.clt.ledmanager.util.Tools;
import com.clt.netmessage.NMDeleteProgramAnswer;
import com.clt.netmessage.NMGetProgramsNamesAnswer;
import com.clt.netmessage.NetMessageType;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.R;

import java.util.ArrayList;
import java.util.List;



public class TerminalProgramFragment extends BaseObserverFragment  {

    private LinearLayout view;
    private ListView listView;

    private int deleteProgramIndex;
    private Dialog deleteProgramDialog;
    private List<String> list;
    private SpinnerAdapter spinnerAdapter;
    private View mView;
    private TextView tvSize;

    private static final boolean DBG = true;
    private static final String TAG = "ProgramManagerFragment";



    @Override
    public void onResume() {
        super.onResume();
        updateProgreamsView();
        initListener();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Replace LinearLayout by the type of the root element of the layout you're trying to load

        view = (LinearLayout) inflater.inflate(R.layout.fragment_program_manager, container, false);
        listView = (ListView) view.findViewById(R.id.program_manager_list_view);

        setHasOptionsMenu(true);

        return view;
    }

    private static final int RESULT_UPLOAD_PROGRAM =4001;


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.fragment_terminalprogram, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()) {

            case R.id.menu_upload_program:

                Intent upload_program = new Intent(getActivity(),EditProgramFragment.class);
                startActivityForResult(upload_program,RESULT_UPLOAD_PROGRAM) ;
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initListener(){

        updateProgreamsView();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Application.getInstance().programs != null && Application.getInstance().programs.size() >= 1) {
                    mangerNetService.setPlayProgram(Application.getInstance().programs.get(position));

                    if (DBG){
                        Log.d(TAG,"programs = " + Application.getInstance().programs);
                    }
                }
            }

        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                deleteProgramIndex = position;
                if (Application.getInstance().programs.get(position) == null) {
                    return false;
                }

                LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
                mView = inflater.inflate(R.layout.program_delete, null);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_submit);
                Button btnCancel = (Button) mView.findViewById(R.id.btn_cancel);
                TextView tvName = (TextView) mView.findViewById(R.id.tv_program_name);
                tvSize = (TextView) mView.findViewById(R.id.tv_program_size);
                TextView tvPath = (TextView) mView.findViewById(R.id.tv_program_path);
                TextView tvFullPath = (TextView) mView.findViewById(R.id.tv_program_full_path);
                tvSize.setText(Tools.byte2KbOrMb(Application.getInstance().programs.get(position).getSize()) + "");

                switch (Application.getInstance().programs.get(position).getPathType()) {
                    case Program.UDISK:// U盘
                        tvPath.setText(getString(R.string.from_usb_disk));
                        break;
                    case Program.SDCARD:
                        tvPath.setText(getString(R.string.from_sd_card));
                        break;
                }
                tvName.setText(Application.getInstance().programs.get(position).getFileName().substring(0, Application.getInstance().programs.get(position).getFileName().lastIndexOf(".")));
                tvFullPath.setText(Application.getInstance().programs.get(position).getPath());

                btnSubmit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (Application.getInstance().programs.get(position) != null) {
                            mangerNetService.deletePlayProgram(Application.getInstance().programs.get(position));
//                            更新数据
                            updateProgreamsView();

                        }
                        if (deleteProgramDialog != null) {
                            deleteProgramDialog.dismiss();
                        }
                    }
                });

                btnCancel.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        if (deleteProgramDialog != null) {
                            deleteProgramDialog.dismiss();
                        }
                    }
                });

                deleteProgramDialog = DialogFactory.createDialog(fragmentActivity, mView);
                deleteProgramDialog.show();
                return true;
            }
        });
    }
    /**
     * 获取节目清单后更新
     */
    private void updateProgreamsView() {

        if (Application.getInstance().programs != null && !Application.getInstance().programs.isEmpty()) {
            int size = Application.getInstance().programs.size();
            list = new ArrayList<>();
            Program program = null;
            for (int i = 0; i < size; i++) {

                program = Application.getInstance().programs.get(i);

                String fileName = program.getFileName();

                String from = null;
                if (program.getPathType() == Program.SDCARD) {
                    from = getString(R.string.from_sd_card);
                } else if (program.getPathType() == Program.UDISK) {
                    from = getString(R.string.from_usb_disk);
                } else if (program.getPathType() == Program.INTERNAL_STORAGE) {
                    from = getString(R.string.from_internal_storage);
                }

                list.add(getProgramName(fileName));

            }
            if (spinnerAdapter == null) {
                spinnerAdapter = new SpinnerAdapter(getActivity(), list);
                listView.setAdapter(spinnerAdapter);
            } else {
                spinnerAdapter.setData(list);
                spinnerAdapter.notifyDataSetChanged();

            }
        } else {
            if (list != null && spinnerAdapter != null) {
                list.clear();
                spinnerAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    protected void dealHandlerMessage(Message netMessage) {
        switch (netMessage.what) {

            case NetMessageType.getProgramsNamesAnswer:// 获得节目信息
            {
                String str = (String) netMessage.obj;
                Gson gsons = new Gson();
                NMGetProgramsNamesAnswer nmGetProgramsNamesAnswer = gsons.fromJson(str, NMGetProgramsNamesAnswer.class);
                Application.getInstance().programs = nmGetProgramsNamesAnswer.getProgramsNames();

                updateProgreamsView();

            }
            break;

            case NetMessageType.deleteProgramAnswer:// 删除节目结果
            {
                String reslut = (String) netMessage.obj;
                Gson mGson = new Gson();
                NMDeleteProgramAnswer nmDeleteProgramAnswer = mGson.fromJson(reslut, NMDeleteProgramAnswer.class);
                if (nmDeleteProgramAnswer.getErrorCode() == 1) {
                    Application.getInstance().programs.remove(deleteProgramIndex);

                    updateProgreamsView();
                    Toast.makeText(fragmentActivity, getResources().getString(R.string.delete_success), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(fragmentActivity, getResources().getString(R.string.delete_fail), Toast.LENGTH_SHORT).show();
                }
            }
            break;

            case NetMessageType.MSG_WHAT_PROGRAM_UPDATE:

                updateProgreamsView();
                break;
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
}
