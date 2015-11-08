package com.clt.commondata;

import java.util.ArrayList;

public class ColorTempToRGBCoef
{

    private ArrayList<SColorTempXYZ> mColorTempXYZTable = new ArrayList<SColorTempXYZ>();

    private ArrayList<SColorTempRGB> mColorTempRGBTable = new ArrayList<SColorTempRGB>();

    public ColorTempToRGBCoef()
    {
        mColorTempXYZTable.add(new SColorTempXYZ(2000, 0.52655f, 0.41340f));
        mColorTempXYZTable.add(new SColorTempXYZ(2275, 0.49822f, 0.41535f));
        mColorTempXYZTable.add(new SColorTempXYZ(2500, 0.47690f, 0.41375f));
        mColorTempXYZTable.add(new SColorTempXYZ(2700, 0.45953f, 0.41061f));
        mColorTempXYZTable.add(new SColorTempXYZ(3000, 0.43474f, 0.40340f));
        mColorTempXYZTable.add(new SColorTempXYZ(3300, 0.41497f, 0.39541f));
        mColorTempXYZTable.add(new SColorTempXYZ(3700, 0.39439f, 0.38107f));
        mColorTempXYZTable.add(new SColorTempXYZ(4000, 0.38040f, 0.37681f));
        mColorTempXYZTable.add(new SColorTempXYZ(4500, 0.35920f, 0.36248f));
        mColorTempXYZTable.add(new SColorTempXYZ(5000, 0.34508f, 0.35168f));
        mColorTempXYZTable.add(new SColorTempXYZ(6000, 0.32434f, 0.33391f));
        mColorTempXYZTable.add(new SColorTempXYZ(6500, 0.33333f, 0.33333f));
        mColorTempXYZTable.add(new SColorTempXYZ(7500, 0.29827f, 0.30816f));
        mColorTempXYZTable.add(new SColorTempXYZ(9200, 0.2248f, 0.29806f));
        mColorTempXYZTable.add(new SColorTempXYZ(10000, 0.19029f, 0.29331f));

        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());
        mColorTempRGBTable.add(new SColorTempRGB());

        InitColorTempTalbe();
    }

    private class SColorTempXYZ
    {
        public int getkTemp()
        {
            return kTemp;
        }

        public void setkTemp(int kTemp)
        {
            this.kTemp = kTemp;
        }

        public float getX()
        {
            return x;
        }

        public void setX(float x)
        {
            this.x = x;
        }

        public float getY()
        {
            return y;
        }

        public void setY(float y)
        {
            this.y = y;
        }

        public float getZ()
        {
            return z;
        }

        public void setZ(float z)
        {
            this.z = z;
        }

        protected int kTemp;

        protected float x;

        protected float y;

        protected float z = 0f;

        public SColorTempXYZ(int kTemp, float x, float y)
        {
            this.kTemp = kTemp;
            this.x = x;
            this.y = y;
        }
    }

    private class SColorTempRGB
    {
        public int getkTemp()
        {
            return kTemp;
        }

        public void setkTemp(int kTemp)
        {
            this.kTemp = kTemp;
        }

        public float getR()
        {
            return r;
        }

        public void setR(float r)
        {
            this.r = r;
        }

        public float getG()
        {
            return g;
        }

        public void setG(float g)
        {
            this.g = g;
        }

        public float getB()
        {
            return b;
        }

        public void setB(float b)
        {
            this.b = b;
        }

        protected int kTemp;

        protected float r;

        protected float g;

        protected float b;
    }

    private void InitColorTempTalbe()
    {
        float fMax = 0.0f;
        for (int i = 0; i < 15; i++)
        {
            SColorTempXYZ colorTempXYZ = mColorTempXYZTable.get(i);
            SColorTempRGB colorTempRGB = mColorTempRGBTable.get(i);

            colorTempXYZ.setZ(1.0f - colorTempXYZ.getX() - colorTempXYZ.getY());

            colorTempRGB.setkTemp(colorTempXYZ.getkTemp());
            fMax = 0.0f;

            if (fMax < colorTempXYZ.getX())
                fMax = colorTempXYZ.getX();

            if (fMax < colorTempXYZ.getY())
                fMax = colorTempXYZ.getY();

            if (fMax < colorTempXYZ.getZ())
                fMax = colorTempXYZ.getZ();

            colorTempRGB.setR(colorTempXYZ.getX() / fMax);
            colorTempRGB.setG(colorTempXYZ.getY() / fMax);
            colorTempRGB.setB(colorTempXYZ.getZ() / fMax);
        }
    }

    public class ColorCoef
    {
        public float r;

        public float g;

        public float b;
    }

    public ColorCoef GetColorTemp(int kTemp)
    {
        ColorCoef colorCoef = new ColorCoef();

        for (int i = 0; i < 15; i++)
        {
            SColorTempRGB colorTempRGB = mColorTempRGBTable.get(i);

            if (kTemp <= colorTempRGB.getkTemp())
            {
                colorCoef.r = colorTempRGB.getR();
                colorCoef.g = colorTempRGB.getG();
                colorCoef.b = colorTempRGB.getB();

                return colorCoef;
            }
        }

        SColorTempRGB colorTempRGB = mColorTempRGBTable.get(14);
        colorCoef.r = colorTempRGB.getR();
        colorCoef.g = colorTempRGB.getG();
        colorCoef.b = colorTempRGB.getB();

        return colorCoef;
    }
}
