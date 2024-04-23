package main;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		try (OutputStream MapOutputSream = new FileOutputStream("map.txt");
				ObjectOutputStream mapObjectInputStream = new ObjectOutputStream(MapOutputSream)) {
			Map<Integer, Integer> idToRecordMap = new HashMap<>();
			mapObjectInputStream.writeObject(idToRecordMap);
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		saveStudent("{'id': 3, 'name': 'Ali'}", 3);
		saveStudent("{'id': 5, 'name': 'Hassan'}", 5);

		System.out.println(loadStudent(3));
	}

	private static String loadStudent(int id) {
		String student = null;
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("students.txt", "rw");
				InputStream MapInputSream = new FileInputStream("map.txt");
				ObjectInputStream mapObjectInputStream = new ObjectInputStream(MapInputSream)) {
			
			Map<Integer, Integer> idToRecordMap = (Map<Integer, Integer>) mapObjectInputStream.readObject();
			int record = idToRecordMap.get(id);
			randomAccessFile.seek(1000 * record);
			byte[] b = new byte[1000];
			randomAccessFile.read(b, 0, 1000);
			student = new String(b).trim();
			
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
		return student;
	}

	private static void saveStudent(String st, int id) {
		try (RandomAccessFile randomAccessFile = new RandomAccessFile("students.txt", "rw")) {
			
			InputStream MapInputSream = new FileInputStream("map.txt");
			ObjectInputStream mapObjectInputStream = new ObjectInputStream(MapInputSream);
			Map<Integer, Integer> idToRecordMap = (Map<Integer, Integer>) mapObjectInputStream.readObject();
			mapObjectInputStream.close();
			
			OutputStream MapOutputSream = new FileOutputStream("map.txt");
			ObjectOutputStream mapObjectOutputStream = new ObjectOutputStream(MapOutputSream);
			int newRecordNumber = idToRecordMap.size();
			idToRecordMap.put(id, newRecordNumber);
			mapObjectOutputStream.writeObject(idToRecordMap);
			mapObjectOutputStream.close();
			
			randomAccessFile.seek(newRecordNumber * 1000);
			randomAccessFile.write(st.getBytes());
			
		} catch (IOException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

}
