package org.example.ServerQueue.QueueCore;

public class Queue {
    private QueueObject head, tail;
    public Queue(){
        head = null;
        tail = null;
    }

    public void enqueue(Job job){
        if(head == null){                 //If head is null that means queue is empty, so tail and head need to be
            head = new QueueObject(job);  //set to the first object in queue
            tail = head;
        }
        else{
            tail.nextQueue = new QueueObject(job);
            tail = tail.nextQueue;
        }
    }

    public Job dequeue(){
        //This job should never be called when the queue is empty.
        //If it is called when the queue is empty then there is a logic flow error earlier in the program
        Job returnJob;
        if(head.getJob() == tail.getJob()){
            returnJob = head.getJob();
            head = null;
            tail = null;
            return returnJob;
        }
        returnJob = head.getJob();  //if head has null job, will exit code here
        head = head.nextQueue;
        return returnJob;
    }

    public boolean isQueueEmpty(){ //checks to see if head is null to check is queue is empty
        if(head == null)
            return true;
        else
            return false;
    }


    public int queueLength(){
        //queueSize is set to one because the while loop will end before adding the tail item to count
        int queueSize = 1;
        QueueObject index = head;
        while(index.getNextQueue() != null){
            queueSize++;
            index = index.nextQueue;
        }
        return queueSize;
    }

    protected class QueueObject{
        private Job job;
        private QueueObject nextQueue;
        public QueueObject(Job job){
            this.job = job;
            nextQueue = null;
        }
        public Job getJob(){
            return job;
        }
        public QueueObject getNextQueue(){
            return nextQueue;
        }
    }
}
