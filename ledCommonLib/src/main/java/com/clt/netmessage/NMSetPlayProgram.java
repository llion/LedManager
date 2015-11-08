package com.clt.netmessage;

import java.io.Serializable;

import com.clt.entity.Program;

public class NMSetPlayProgram extends NMBase implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1907436766600970023L;

    protected Program program;

    
    public Program getProgram()
    {
        return program;
    }


    public void setProgram(Program program)
    {
        this.program = program;
    }


    public NMSetPlayProgram()
    {
        mType = NetMessageType.SetPlayProgram;
    }

}
