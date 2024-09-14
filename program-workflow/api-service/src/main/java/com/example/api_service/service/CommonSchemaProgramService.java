package com.example.api_service.service;

import com.example.api_service.model.CommonSchemaProgram;
import com.example.api_service.repository.CommonSchemaProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CommonSchemaProgramService {

    private static final Logger logger = LoggerFactory.getLogger(CommonSchemaProgramService.class);

    @Autowired
    private CommonSchemaProgramRepository repository;

    public List<CommonSchemaProgram> getFullSyncData(String month) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        try {
            Date start = sdf.parse(month);
            Calendar cal = Calendar.getInstance();
            cal.setTime(start);
            cal.set(Calendar.DAY_OF_MONTH, 1);  
            Date monthStart = cal.getTime();
            
            cal.add(Calendar.MONTH, 1); 
            cal.add(Calendar.DAY_OF_MONTH, -1); 
            Date monthEnd = cal.getTime();

            return repository.findByUpdatedDateBetween(monthStart, monthEnd);
        } catch (ParseException e) {
            logger.error("Error parsing date", e);
            return List.of(); 
        }
    }

    public List<CommonSchemaProgram> getDeltaSyncDataBetween(Date startDate, Date endDate) {
        Date start = getStartOfDay(startDate);
        Date end = getStartOfDay(endDate);

        Calendar cal = Calendar.getInstance();
        cal.setTime(end);
        cal.add(Calendar.DAY_OF_MONTH, 1); 
        end = cal.getTime();

        return repository.findByUpdatedDateBetween(start, end);
    }

    private Date getStartOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }
}

