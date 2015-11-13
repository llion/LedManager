package com.clt.ledmanager.app.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
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


public class ProgramManagerFragment extends BaseObserverFragment {

    private LinearLayout view;
    private ListView listView;

    private int deleteProgramIndex;
    private Dialog deleteProgramDialog;
    private String[] arrProgramsNames;
    private List<String> list;
    private SpinnerAdapter spinnerAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        updateProgreamsView();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Replace LinearLayout by the type of the root element of the layout you're trying to load
        view = (LinearLayout) inflater.inflate(R.layout.fragment_program_manager, container, false);
        listView = (ListView) view.findViewById(R.id.program_manager_list_view);
        /**
         * 节目切换
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (Application.getInstance().programs != null && Application.getInstance().programs.size() >= 1) {
                    mangerNetService.setPlayProgram(Application.getInstance().programs.get(position));
                }
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                deleteProgramIndex = position;
                final Program program = Application.getInstance().programs.get(position);
                if (program == null) {
                    return false;
                }

                LayoutInflater inflater = LayoutInflater.from(fragmentActivity);
                View mView = inflater.inflate(R.layout.program_delete, null);
                Button btnSubmit = (Button) mView.findViewById(R.id.btn_submit);
                Button btnCancel = (Button) mView.findViewById(R.id.btn_cancel);
                TextView tvName = (TextView) mView.findViewById(R.id.tv_program_name);
                TextView tvSize = (TextView) mView.findViewById(R.id.tv_program_size);
                TextView tvPath = (TextView) mView.findViewById(R.id.tv_program_path);
                TextView tvFullPath = (TextView) mView.findViewById(R.id.tv_program_full_path);
                tvSize.setText(Tools.byte2KbOrMb(program.getSize()) + "");

                switch (program.getPathType()) {
                    case Program.UDISK:// U盘
                        tvPath.setText(getString(R.string.from_usb_disk));
                        break;
                    case Program.SDCARD:
                        tvPath.setText(getString(R.string.from_sd_card));
                        break;
                }
                tvName.setText(program.getFileName().substring(0, program.getFileName().lastIndexOf(".")));
                tvFullPath.setText(program.getPath());

                btnSubmit.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        if (program != null) {
                            mangerNetService.deletePlayProgram(program);
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
        return view;
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
                listView.setAdapter(new SpinnerAdapter(getActivity(), list));
            } else {
                spinnerAdapter.notifyDataSetChanged();
            }
        } else {
            if (list != null && spinnerAdapter != null) {
                list.clear();
                spinnerAdapter.notifyDataSetChanged();
            }
        }
    }

    private void init() {


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
                NMDeleteProgramAnswer nmDeleteProgramAnswer = mGson
                        .fromJson(reslut, NMDeleteProgramAnswer.class);
                if (nmDeleteProgramAnswer.getErrorCode() == 1) {
                    Application.getInstance().programs.remove(deleteProgramIndex);
                    updateProgreamsView();
                    Toast.makeText(
                            fragmentActivity,
                            getResources().getString(
                                    R.string.delete_success), Toast.LENGTH_SHORT).show();

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
