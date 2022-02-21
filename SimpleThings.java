package simpleTextEditor;

import java.util.Stack;

public class SimpleThings {
	// Append, Move, Backspace
	// Copy, Paste, Cut
	// Undo, Redo
	public static void main(String[] args) {
		String[][] append = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}};
		
		String[][] move = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}};
		
		String[][] backspace = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}};
		
		String[][] copyPaste = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}, {"COPY", "5", "6"}, {"PASTE"},
				{"PASTE"},{"PASTE", "10"}};
		
		String[][] cutPaste = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}, {"CUT", "5", "6"},{"PASTE", "17"},
				{"PASTE"}};
		
		String[][] undo = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}, {"UNDO"}, {"CUT", "5", "6"},{"PASTE", "17"},
				{"PASTE"}};
		
		String[][] undoB = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}, {"UNDO"}, {"UNDO"}, {"UNDO"},
				{"CUT", "5", "6"},{"PASTE", "17"}, {"PASTE"}};
		
		String[][] redo = {{"APPEND", "Let's"}, {"APPEND", " party"}, {"APPEND", ", "}, 
				{"APPEND", "everyone!"}, {"MOVE", "0"}, {"APPEND", "Bill: "}, {"MOVE", "77"},
				{"APPEND", "!!!"}, {"MOVE", "6"}, {"DELETE"}, {"UNDO"}, {"UNDO"}, {"UNDO"}, {"REDO"},
				{"CUT", "5", "6"},{"PASTE", "17"}, {"PASTE"}};
		
		
		
		System.out.println(process(append));
		System.out.println(process(move));
		System.out.println(process(backspace));
		System.out.println(process(copyPaste));
		System.out.println(process(cutPaste));
		System.out.println(process(undo));
		System.out.println(process(undoB));
		System.out.println(process(redo));

	}

	//To see each process, uncomment system.out.print() statements
	public static String process(String[][] a) {
		String result = "";
		int size = a.length;
		int position = -1;
		String temp = "";
		Stack<String> undo = new Stack<String>();
		String redo = "";
		
		
		for(int i = 0; i<size;i++) {
			
			
			
			if(a[i][0].equalsIgnoreCase("APPEND") ) {
//				System.out.println("APPEND");
				undo.add(result);
				if(position == -1) {
					result = result += a[i][1];
				}
				else {
					temp = result;
					String insert = a[i][1];
					result = temp.substring(0, position) + insert + temp.substring(position);
				}
				redo = result;
			}//append
			
			else if(a[i][0].equalsIgnoreCase("MOVE")) {
//				System.out.println("MOVE");
				position = changePosition(a[i][1],result);
			}//move
			
			else if(a[i][0].equalsIgnoreCase("DELETE")) {
//				System.out.println("DELETE");
				undo.add(result);
				if(position == -1) {
					temp = result;
					result = temp.substring(0,temp.length()-1);
				}
				else {
					undo.add(result);
					temp = result;
					result = temp.substring(0,position-1) + temp.substring(position);
				}
				redo = result;
			}//delete
			
			else if(a[i][0].equalsIgnoreCase("COPY")) {
//				System.out.println("COPY");
				temp = result.substring(Integer.parseInt(a[i][1]),(Integer.parseInt(a[i][2])));
			}//copy
			
			
			else if(a[i][0].equalsIgnoreCase("CUT")) {
//				System.out.println("CUT");
				undo.add(result);
				temp = result.substring(Integer.parseInt(a[i][1]),Integer.parseInt(a[i][2]));
				String hold = result;
				result = hold.substring(0,Integer.parseInt(a[i][1])) + 
						hold.substring(Integer.parseInt(a[i][2]));
				redo = result;
			}//cut
			
			else if(a[i][0].equalsIgnoreCase("PASTE")){
//				System.out.println("PASTE");
				undo.add(result);
				String hold = result;
				if(a[i].length > 1) {
					position = changePosition(a[i][1],result);
					result = hold.substring(0,position) + temp + hold.substring(position);
				
				}
				else {
					undo.add(result);
					result = hold.substring(0,position) + temp + hold.substring(position);
				}
				redo = result;
			}//paste
			
			else if(a[i][0].equalsIgnoreCase("UNDO")) {
//				System.out.println("UNDO");
				redo = result;
				
				if(!undo.isEmpty()) {
					result = undo.pop();
				}
				else {
					System.out.println("No undos.");
				}
			}//undo
			
			else if(a[i][0].equalsIgnoreCase("REDO")) {
//				System.out.println("REDO");
				result = redo;
			}//undo
			
//			System.out.println("RESULT IS: " + result + "\n------------");
		}
		
		return result;
	}//process
	
	
	public static int changePosition(String num, String edit) {
		int value = Integer.parseInt(num);
		int length = edit.length();
		int change = 0;
		
		if(value > length) {
			change = length;
		}
		else if (value < 0) {
			change = 0;
		}
		else {
			change = value;
		}
		
		
		return change;
	}

	
	
}


