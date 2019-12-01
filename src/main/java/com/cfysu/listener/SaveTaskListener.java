package com.cfysu.listener;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import com.cfysu.model.Task;
import com.cfysu.task.FetchTask;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @Date 2019/12/1
 */
@Slf4j
@Component
public class SaveTaskListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent) {
        log.info("save runtime task to disk...");
        FetchTask fetchTask = (FetchTask)contextClosedEvent.getApplicationContext().getBean("fetchTask");
        Task task = Task.builder().category(fetchTask.getCategory()).currentPage(fetchTask.getCurrentPage()).build();
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream("/upload/task.data"));
            objectOutputStream.writeObject(task);
        } catch (IOException e) {
            log.error("objectOutputStream.writeObject", e);
        }finally {
            if(objectOutputStream != null){
                try {
                    objectOutputStream.close();
                } catch (IOException e1) {
                    log.error("objectOutputStream.close", e1);
                }
            }
        }
    }
}
