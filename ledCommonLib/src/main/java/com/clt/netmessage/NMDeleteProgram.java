package com.clt.netmessage;

import java.io.Serializable;

import com.clt.entity.Program;

public class NMDeleteProgram extends NMBase implements Serializable
{



    /**
     * 
     */
    private static final long serialVersionUID = 3497686382084419956L;

    protected Program program;

    
    public Program getProgram()
    {
        return program;
    }


    public void setProgram(Program program)
    {
        this.program = program;
    }


    public NMDeleteProgram()
    {
        mType = NetMessageType.deleteProgram;
    }

    
}
