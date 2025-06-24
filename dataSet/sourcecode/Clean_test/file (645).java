import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by szhu on 2014-03-07.
 */
public class CopyRandomList{
    public static void main(String []args){
        CopyRandomList copyRandomList = new CopyRandomList();
        RandomListNode [] randomListNodes = {new RandomListNode(1), new RandomListNode(2), new RandomListNode(3)};
        randomListNodes[0].next = randomListNodes[1];
        randomListNodes[1].next = randomListNodes[2];
        randomListNodes[2].next = null;

        randomListNodes[0].random = randomListNodes[2];
        randomListNodes[1].random = randomListNodes[0];
        randomListNodes[2].random = randomListNodes[2];

        copyRandomList.copyRandomList(randomListNodes[0]);
    }
    public RandomListNode copyRandomList(RandomListNode head) {
        if(head == null)
            return head;
        HashMap<RandomListNode, RandomListNode> map = new HashMap<RandomListNode, RandomListNode>();
        RandomListNode p = head;
        while(p != null){
            RandomListNode node = new RandomListNode(p.label);
            map.put(p, node);
            p = p.next;
        }
        p = head;
        while(p != null){
            if(p.next == null)
                map.get(p).next = null;
            else
                map.get(p).next = map.get(p.next);
            if(p.random == null)
                map.get(p).random = null;
            else
                map.get(p).random = map.get(p.random);
            p = p.next;
        }
        return map.get(head);
    }

/*    public RandomListNode copyRandomList(RandomListNode head) {
        ArrayList<RandomListNode> originalLink = new ArrayList<RandomListNode>();
        ArrayList<RandomListNode> copiedLink = new ArrayList<RandomListNode>();
        RandomListNode p = head;
        RandomListNode result = new RandomListNode(0), result2=result;
        result.next = null;
        while(p != null){
            if(originalLink.contains(p)){
                result.next = copiedLink.get(originalLink.indexOf(p));
            }else{
                RandomListNode node = new RandomListNode(p.label);
                originalLink.add(p);
                copiedLink.add(node);
                result.next = node;
            }
            result = result.next;
            if(p.random != null && !originalLink.contains(p.random)){
                RandomListNode node2 = new RandomListNode(p.random.label);
                result.random = node2;
                originalLink.add(p.random);
                copiedLink.add(node2);
            }else if (p.random == null){
                result.random = null;
            }else{
                result.random = copiedLink.get(originalLink.indexOf(p.random));
            }
            p = p.next;
        }
        return result2.next;
    }*/
}
