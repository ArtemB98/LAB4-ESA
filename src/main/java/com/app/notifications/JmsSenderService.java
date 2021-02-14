package com.app.notifications;

import com.app.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsSenderService {

    private final JmsTemplate jmsTemplate;

    @Autowired
    public JmsSenderService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendEmployeeUpdate(Employee employee, EventType eventType){
        Email email = new Email();
        Department department = employee.getDepartmentByDepartmentId();
        email.setReceiver(employee.getEmail());
        email.setSubject("Employee [" + eventType.name() + ']');
        String bodyPattern = "Здравствуйте, %s %s!\n\n" +
                "Данные сотрудника изменены!\n" +
                "Тип изменения: %s\n\n" +
                "Сотрудник: %s";
        String body = String.format(bodyPattern,
                employee.getName(), employee.getSalary(), eventType.name(), department.toString());
        email.setBody(body);
        jmsTemplate.convertAndSend("mailbox", email);
    }

    public <T> void sendEvent(Class<T> entityClass, T entity, EventType eventType){
        Event event = new Event();
        event.setEventType(eventType);
        event.setEntity(entity.toString());
        event.setEntityClass(entityClass.getSimpleName());
        jmsTemplate.convertAndSend("eventbox", event);
    }
}
