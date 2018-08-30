package com.cdominguez.dev.cardnumber.ocr;

import android.util.Log;
import android.util.SparseArray;

import com.cdominguez.dev.cardnumber.ocr.camera.GraphicOverlay;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OcrDetectorProcessor implements Detector.Processor<TextBlock> {

    private GraphicOverlay<OcrGraphic> graphicOverlay;
    private OcrCaptureActivity.onTextCardNumberDetector onTextCardNmberDetector;

    public void setOnTextCardNmberDetector(OcrCaptureActivity.onTextCardNumberDetector detector){
        this.onTextCardNmberDetector = detector;
    }

    OcrDetectorProcessor(GraphicOverlay<OcrGraphic> ocrGraphicOverlay) {
        graphicOverlay = ocrGraphicOverlay;
    }

    @Override
    public void release() {
        graphicOverlay.clear();
    }

    @Override
    public void receiveDetections(Detector.Detections<TextBlock> detections) {

        graphicOverlay.clear();
        SparseArray<TextBlock> items = detections.getDetectedItems();

        if (items.size() == 0){
            onTextCardNmberDetector.onTextCardNumberDetector("release");
        }

        for (int i = 0; i < items.size(); ++i) {

            TextBlock item = items.valueAt(i);
            if (item != null && item.getValue() != null) {

                if (isNumber(item.getValue())){

                    Log.i("Processor", "Text detected! " + item.getValue() + " size " + item.getValue().length());
                    OcrGraphic graphic = new OcrGraphic(graphicOverlay, item);
                    graphicOverlay.add(graphic);
                    onTextCardNmberDetector.onTextCardNumberDetector(item.getValue());
                }else{

                    onTextCardNmberDetector.onTextCardNumberDetector("null");
                }
            }
        }
    }

    private boolean isNumber(String value) {
        boolean ban = false;

        if (value.length() >= 16){

            Pattern pat = Pattern.compile("^\\d{4}.*[\\d]$");

            Matcher matcher = pat.matcher(value);
            if (matcher.find()){
                ban = true;
            }
        }

        return ban;
    }
}
