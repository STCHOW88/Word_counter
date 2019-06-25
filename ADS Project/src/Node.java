/*Yingjie Ni
 * UFID: 41267952
 * Date: Nov.15 2018
 */
public class Node
{
        int degree = 0;       
	    Node child, parent, left, right; // create reference to child, parent, left and right 
        boolean cascadingCut = false; 
        private String hash;
        int key;

        Node(String hash,int key)
        {
           this.left = this;
           this.right = this;// initialize references
           this.degree = 0;
           this.hash = hash;
           this.key = key;

        }

        public  String  getHashTag(){
            return this.hash;
        }// method to get hash tag

    }