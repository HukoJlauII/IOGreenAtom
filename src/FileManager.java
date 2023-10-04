import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;

public class FileManager {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Введите путь к файлу, ключ операции (-create, -delete, -read, -write) и содержимое (в случае -write):");
            String input = in.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                break;
            }

            String[] parts = input.split(" ", 3);
            if (parts.length < 2) {
                System.out.println("Неверный формат ввода. Ожидался ввод в формате 'путь_к_файлу ключ_операции [содержимое]' с учетом необходимости содержимого.");
                continue;
            }

            String filePath = parts[0];
            String operation = parts[1];
            String content = parts.length == 3 ? parts[2] : "";

            File file = new File(filePath);

            try {
                switch (operation) {
                    case "-create":
                        if (file.createNewFile()) {
                            System.out.println("Файл успешно создан.");
                        } else {
                            throw new FileAlreadyExistsException("Файл с таким названием уже существует");
                        }
                        break;

                    case "-delete":
                        if (file.delete()) {
                            System.out.println("Файл успешно удален.");
                        } else {
                            throw new FileAlreadyExistsException("Файла,находящегося по такому пути не существует");
                        }
                        break;

                    case "-read":
                        FileReader fr = new FileReader(file);
                        BufferedReader br = new BufferedReader(fr);

                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }

                        br.close();
                        fr.close();
                        break;

                    case "-write":
                        FileWriter fw = new FileWriter(file);
                        BufferedWriter bw = new BufferedWriter(fw);

                        bw.write(content);

                        bw.close();
                        fw.close();

                        System.out.println("Содержимое успешно записано в файл.");
                        break;

                    default:
                        System.out.println("Неверный ключ операции.");
                        break;
                }
            } catch (IOException e) {
                System.out.println("Произошла ошибка "+ e.getClass().getName() +":" + e.getMessage());
            }
        }

        in.close();
        System.out.println("Программа завершена.");
    }
}
