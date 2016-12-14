import java.util.Hashtable;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
public class homework_part4 {
    public class SeparateChainingHashST<Key, Value> {
        private static final int INIT_CAPACITY = 4;

        
        // };

        private int N;                                // number of key-value pairs
        private int M;                                // hash table size
        private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables


        // create separate chaining hash table
        public SeparateChainingHashST() {
            this(INIT_CAPACITY);
        }

        // create separate chaining hash table with M lists
        public SeparateChainingHashST(int M) {
            this.M = M;
            st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[M];
            for (int i = 0; i < M; i++)
                st[i] = new SequentialSearchST<Key, Value>();
        }

        // resize the hash table to have the given number of chains b rehashing all of the keys
        private void resize(int chains) {
            SeparateChainingHashST<Key, Value> temp = new SeparateChainingHashST<Key, Value>(chains);
            for (int i = 0; i < M; i++) {
                for (Key key : st[i].key()) {
                    temp.put(key, st[i].get(key));
                }
            }
            this.M = temp.M;
            this.N = temp.N;
            this.st = temp.st;
        }

        // hash value between 0 and M-1
        private int hash(Key key) {
            return (key.hashCode() & 0x7fffffff) % M;
        }

        // return number of key-value pairs in symbol table
        public int size() {
            return N;
        }

        // is the symbol table empty?
        public boolean isEmpty() {
            return size() == 0;
        }

        // is the key in the symbol table?
        public boolean contains(Key key) {
            return get(key) != null;
        }

        // return value associated with key, null if no such key
        public Value get(Key key) {
            int i = hash(key);
            return(Value) st[i].get(key);
        }

        // insert key-value pair into the table
        public void put(Key key, Value val) {
            if (val == null) {
                delete(key);
                return;
            }

            // double table size if average length of list >= 10
            if (N >= 10 * M) resize(2 * M);

            int i = hash(key);
            if (!st[i].contains(key)) N++;
            st[i].put(key, val);
        }

        // delete key (and associated value) if key is in the table
        public void delete(Key key) {
            int i = hash(key);
            if (st[i].contains(key)) N--;
            st[i].delete(key);

            // halve table size if average length of list <= 1
            if (M > INIT_CAPACITY && N <= 2 * M) resize(M / 2);
        }

        // return keys in symbol table as an Iterable
        public Iterable<Key> keys() {
            Queue<Key> queue = new Queue<Key>();
            for (int i = 0; i < M; i++) {
                for (Key key : st[i].keys())
                    queue.enqueue(key);
            }
            return queue;
        }

        public long getCollisions() {
            long counter = 0;
            for (int i = 0; i < M; i++) {
                if (st[i].length > 1) {
                    counter += st[i].length; // 2 (or more) items collided in this bucket.
                }
            }
            return counter;
        }

        /***********************************************************************
         *  Unit test client.
         ***********************************************************************/
        public static void main(String[] args){ 
            String file = args[0];
            BuffereadReader br = null;
            String line = "";
            String fileSplitBy = " ";
            SeparateChainingHashST<String, String> st = new SeparateChainingHashST<String, String>();
            try {
                br = new BufferedReader(new FileReader(“file.txt”));
                while ((line = br.readLine()) != null) {
                    String[] dmaRow = line.split(filesplitBy);
                    String dma = dmaRow[0];
                    String city = dmaRow[1];
                    String state = dmaRow[2];
                    city = st.get(key);
                    

                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();

            } catch (IOException e) {
                e.printStackTrace();

            } catch (NumberFormateException e) {
                e.printStackTrace();

            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
           

              
     
          
            // print keys
            for (String s : st.keys())
                System.out.println(s + " " + st.get(s));

        }

    }
}