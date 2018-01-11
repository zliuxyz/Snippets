/**
 * Created by harding on 2017-05-21.
 */
package zongming.snippets;

public class AddTwoNumbers {

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) {
            val = x;
        }
    }


    private ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode head = new ListNode(0);
        ListNode temp = head;
        int sum = 0;
        while (c1 != null || c2 != null) {
            sum /= 10;
            if (c1 != null) {
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            temp.next = new ListNode(sum % 10);
            temp = temp.next;
        }
        if (sum / 10 == 1) {
            temp.next = new ListNode(1);
        }
        return head.next;
    }

    public static void main(String[] args) {
        AddTwoNumbers test = new AddTwoNumbers();

        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode result = test.addTwoNumbers(l1, l2);
        System.out.println(result.val);
    }

}
