package com.example.appapi.Models;

import java.util.List;

public class Quiz {
    private Trolls trolls;
    private List<Math1> maths;

    public Quiz(Trolls trolls, List<Math1> maths) {
        this.trolls = trolls;
        this.maths = maths;
    }

    public Trolls getTrolls() {
        return trolls;
    }

    public void setTrolls(Trolls trolls) {
        this.trolls = trolls;
    }

    public List<Math1> getMaths() {
        return maths;
    }

    public void setMaths(List<Math1> maths) {
        this.maths = maths;
    }
}

