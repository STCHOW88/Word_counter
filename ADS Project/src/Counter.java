/*Yingjie Ni
 * UFID: 41267952
 * Date: Nov.15 2018
 */
import java.io.*;
import java.util.*;
import java.util.Hashtable;

public class Counter {

	private static BufferedReader br;

	public static void main(String[] args) {

		File file = new File("output_file.txt");
		BufferedWriter writer = null;// create buffered writer

		Hashtable<String, Node> ht = new Hashtable<String, Node>();// hash table

		FibonacciHeap fh = new FibonacciHeap();// fibonacci heap

		try {

			br = new BufferedReader(new FileReader(args[0]));// when you input path of file please use " "

			String s = br.readLine();

			writer = new BufferedWriter(new FileWriter(file));// output

			while (s != null) {

				if (s.equalsIgnoreCase("stop")) {
					break;
				} // the first case which string == "stop"

				else if (s.charAt(0) == '$') {

					String arr[] = s.split(" ");
					String hashTag = arr[0].substring(1);
					int value = Integer.parseInt(arr[1].trim());

					if (!ht.containsKey(hashTag)) {
						Node node = new Node(hashTag, value);
						fh.insert(node);
						ht.put(hashTag, node);/*the second case which string start with '$' add to fibonacci heap and
												hash table*/

					} else if (value > 0) {

						fh.increaseKey(ht.get(hashTag), value);// if the name is already exist then increase key
					}
				} else if (s.charAt(0) != 's') {

					int query = Integer.parseInt(s.trim());

					ArrayList<Node> extract = new ArrayList<Node>(query);

					for (int i = 0; i < query; i++) {

						Node node = fh.removeMax();

						ht.remove(node.getHashTag());

						Node newNode = new Node(node.getHashTag(), node.key);/*create an array of extracted nodes to
																			 insert*/

						extract.add(newNode);

						if (i < query - 1) {
							writer.write(node.getHashTag() + ", ");

						}

						else {

							writer.write(node.getHashTag());

						}

					}

					for (Node i : extract) {

						fh.insert(i);
						ht.put(i.getHashTag(), i);

					}

					writer.newLine();
				}

				s = br.readLine();// read next line
			}
		}

		catch (Exception e) {
			System.out.println(e);
		}

		if (writer != null) {
			try {
				writer.close();// close writer
			} catch (IOException ioe2) {

			}
		}
	}
}
