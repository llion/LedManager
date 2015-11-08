package com.clt.netmessage;

import java.io.Serializable;
import java.util.ArrayList;

import com.clt.entity.Program;

public class NMGetProgramsNamesAnswer extends NMAnswer implements
        Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -1871513145547512650L;

    private ArrayList<Program> programsNames;

    public ArrayList<Program> getProgramsNames()
    {
        return programsNames;
    }

    public void setProgramsNames(ArrayList<Program> programsNames)
    {
        this.programsNames = programsNames;
    }

    public NMGetProgramsNamesAnswer()
    {
        mType = NetMessageType.getProgramsNamesAnswer;
    }

}
