package com.example.weeknotes;


public class Note {
    private int id;
    private final String title;
    private final String description;
    private final DayOfWeek dayOfWeek;
    private final Priority priority;

    public enum DayOfWeek {
        SUNDAY ("Воскресенье"),
        MONDAY ("Понедельник"),
        TUESDAY ("Вторник"),
        WEDNESDAY ("Среда"),
        THURSDAY ("Четверг"),
        FRIDAY ("Пятница"),
        SATURDAY ("Суббота");

        private final String title;

        DayOfWeek(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        //Возвращает DayOfWeek по названию дня
        public static DayOfWeek getDay(String dayTitle){
            for (DayOfWeek item : DayOfWeek.values()) {
                if(item.title.equals(dayTitle)){
                    return item;
                }
            }
            return DayOfWeek.MONDAY;
        }
    }

    public enum Priority {
        LOW("Низкий"),
        MEDIUM("Средний"),
        HIGH("Высокий");

        private final String title;

        Priority(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }

        //Возвращает Priority по названию приоритета
        public static Priority getPriority(String dayTitle){
            for (Priority item : Priority.values()) {
                if(item.title.equals(dayTitle)){
                    return item;
                }
            }
            return Priority.LOW;
        }
    }

    public Note(int id, String title, String description, DayOfWeek dayOfWeek, Priority priority) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dayOfWeek = dayOfWeek;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public Priority getPriority() {
        return priority;
    }
}
