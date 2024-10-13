package com.swlo.token;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class EntryAnalyser {
    BufferedReader reader;

    public EntryAnalyser(String filePath) {
        try {
            reader = new BufferedReader(new FileReader(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int readNextChar() {
        try {
            return reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void mark(int readAheadLimit) throws IOException {
        reader.mark(readAheadLimit);
    }

    public void reset() throws IOException {
        reader.reset();
    }
}
