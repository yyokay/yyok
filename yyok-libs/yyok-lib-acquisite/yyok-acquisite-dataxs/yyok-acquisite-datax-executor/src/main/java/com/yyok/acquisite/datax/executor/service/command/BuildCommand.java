package com.yyok.acquisite.datax.executor.service.command;

import com.yyok.acquisite.datax.core.biz.model.TriggerParam;
import com.yyok.acquisite.datax.core.enums.IncrementTypeEnum;
import com.yyok.acquisite.datax.core.log.JobLogger;
import com.yyok.acquisite.datax.core.util.Constants;
import com.yyok.acquisite.datax.executor.util.SystemUtils;
import com.yyok.share.framework.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static com.yyok.acquisite.datax.core.util.Constants.SPLIT_COMMA;
import static com.yyok.acquisite.datax.executor.service.jobhandler.DataXConstant.*;

public class BuildCommand {

    /**
     * DataX command build
     * @param tgParam
     * @param tmpFilePath
     * @param dataXPyPath
     * @return
     */
    public static String[] buildDataXExecutorCmd(TriggerParam tgParam, String tmpFilePath, String dataXPyPath) {
        // command process
        //"--loglevel=debug"
        List<String> cmdArr = new ArrayList<>();
        cmdArr.add("python");
        String dataXHomePath = SystemUtils.getDataXHomePath();
        if (StringUtils.isNotEmpty(dataXHomePath)) {
            dataXPyPath = dataXHomePath.contains("bin") ? dataXHomePath + DEFAULT_DATAX_PY : dataXHomePath + "bin" + File.separator + DEFAULT_DATAX_PY;
        }
        cmdArr.add(dataXPyPath);
        String doc = buildDataXParam(tgParam);
        if (StringUtils.isNotBlank(doc)) {
            cmdArr.add(doc.replaceAll(SPLIT_SPACE, TRANSFORM_SPLIT_SPACE));
        }
        cmdArr.add(tmpFilePath);
        return cmdArr.toArray(new String[cmdArr.size()]);
    }

    private static String buildDataXParam(TriggerParam tgParam) {
        StringBuilder doc = new StringBuilder();
        String jvmParam = StringUtils.isNotBlank(tgParam.getJvmParam()) ? tgParam.getJvmParam().trim() : tgParam.getJvmParam();
        String partitionStr = tgParam.getPartitionInfo();
        if (StringUtils.isNotBlank(jvmParam)) {
            doc.append(JVM_CM).append(TRANSFORM_QUOTES).append(jvmParam).append(TRANSFORM_QUOTES);
        }

        Integer incrementType = tgParam.getIncrementType();
        String replaceParam = StringUtils.isNotBlank(tgParam.getReplaceParam()) ? tgParam.getReplaceParam().trim() : null;

        if (incrementType != null && replaceParam != null) {

            if (IncrementTypeEnum.TIME.getCode() == incrementType) {
                if (doc.length() > 0) {
                    doc.append(SPLIT_SPACE);
                }
                String replaceParamType = tgParam.getReplaceParamType();

                if (StringUtils.isBlank(replaceParamType) || replaceParamType.equals("Timestamp")) {
                    long startTime = tgParam.getStartTime().getTime() / 1000;
                    long endTime = tgParam.getTriggerTime().getTime() / 1000;
                    doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat(replaceParamType);
                    String endTime = sdf.format(tgParam.getTriggerTime()).replaceAll(SPLIT_SPACE, PERCENT);
                    String startTime = sdf.format(tgParam.getStartTime()).replaceAll(SPLIT_SPACE, PERCENT);
                    doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startTime, endTime));
                }
                //buildPartitionCM(doc, partitionStr);
                doc.append(TRANSFORM_QUOTES);

            } else if (IncrementTypeEnum.ID.getCode() == incrementType) {
                long startId = tgParam.getStartId();
                long endId = tgParam.getEndId();
                if (doc.length() > 0) {
                    doc.append(SPLIT_SPACE);
                }
                doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(replaceParam, startId, endId));
                doc.append(TRANSFORM_QUOTES);
            }
        }

        if (incrementType != null && IncrementTypeEnum.PARTITION.getCode() == incrementType) {
            if (StringUtils.isNotBlank(partitionStr)) {
                List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
                if (doc.length() > 0) {
                    doc.append(SPLIT_SPACE);
                }
                doc.append(PARAMS_CM).append(TRANSFORM_QUOTES).append(String.format(PARAMS_CM_V_PT, buildPartition(partitionInfo))).append(TRANSFORM_QUOTES);
            }
        }

        JobLogger.log("------------------Command parameters:" + doc);
        return doc.toString();
    }


    private void buildPartitionCM(StringBuilder doc, String partitionStr) {
        if (StringUtils.isNotBlank(partitionStr)) {
            doc.append(SPLIT_SPACE);
            List<String> partitionInfo = Arrays.asList(partitionStr.split(SPLIT_COMMA));
            doc.append(String.format(PARAMS_CM_V_PT, buildPartition(partitionInfo)));
        }
    }

    private static String buildPartition(List<String> partitionInfo) {
        String field = partitionInfo.get(0);
        int timeOffset = Integer.parseInt(partitionInfo.get(1));
        String timeFormat = partitionInfo.get(2);
        String partitionTime = DateUtils.format(DateUtils.addDays(new Date(), timeOffset), timeFormat);
        return field + Constants.EQUAL + partitionTime;
    }

}
