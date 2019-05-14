package clock;

import java.awt.event.*;
import javax.swing.Timer;
import priorityqueue.PriorityItem;
import priorityqueue.PriorityQueue;
import priorityqueue.QueueOverflowException;
import priorityqueue.QueueUnderflowException;
import priorityqueue.SortedArrayPriorityQueue;

//controller acts as the brains of the application and speaks to both the view and model class
public class Controller<T> implements PriorityQueue<T> {
    
    //used to add the necessary varibles for the priority queue methods
    PriorityQueue<Alarms> q = new SortedArrayPriorityQueue<>(8);
    private final Object[] storage;
    private final int capacity;
    private int tailIndex;
    int size;
    
    ActionListener listener;
    Timer timer;
    
    //defining the model and view classes
    Model model;
    View view;
    
    public Controller(Model m, View v) {
        storage = new Object[size];
        capacity = size;
        tailIndex = -1;
        
        model = m;
        view = v;
        
        listener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                model.update();
            }
        };
        
        timer = new Timer(100, listener);
        timer.start();
    }
    
    @Override
    public void add(T item, int priority) throws QueueOverflowException {
        tailIndex = tailIndex + 1;
        if (tailIndex >= capacity) {
            /* No resizing implemented, but that would be a good enhancement. */
            tailIndex = tailIndex - 1;
            throw new QueueOverflowException();
        } else {
            /* Scan backwards looking for insertion point */
            int i = tailIndex;
            while (i > 0 && ((PriorityItem<T>) storage[i - 1]).getPriority() < priority) {
                storage[i] = storage[i - 1];
                i = i - 1;
            }
            storage[i] = new PriorityItem<>(item, priority);
        }
    }

    @Override
    public T head() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            return ((PriorityItem<T>) storage[0]).getItem();
        }
    }

    @Override
    public void remove() throws QueueUnderflowException {
        if (isEmpty()) {
            throw new QueueUnderflowException();
        } else {
            for (int i = 0; i < tailIndex; i++) {
                storage[i] = storage[i + 1];
            }
            tailIndex = tailIndex - 1;
        }
    }

    @Override
    public boolean isEmpty() {
        return tailIndex < 0;
    }

    @Override
    public String toString() {
        String result = "[";
        for (int i = 0; i <= tailIndex; i++) {
            if (i > 0) {
                result = result + ", ";
            }
            result = result + storage[i];
        }
        result = result + "]";
        return result;
    }
    
}