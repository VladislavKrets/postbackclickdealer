package online.omnia.clickdealer;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Date;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by lollipop on 24.10.2017.
 */
public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter file name:");
        XSSFWorkbook hssfWorkbook = new XSSFWorkbook(new FileInputStream(reader.readLine()));
        XSSFSheet hssfSheet = hssfWorkbook.getSheetAt(hssfWorkbook.getActiveSheetIndex());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/dd/yy hh:mm:ss a");
        XSSFRow hssfRow;
        PostBackEntity postBackEntity;
        java.util.Date date;
        CurrencyEntity currencyEntity;
        ExchangeEntity exchangeEntity;
        for (int i = 1; i <= hssfSheet.getLastRowNum(); i++) {
            hssfRow = hssfSheet.getRow(i);
            postBackEntity = new PostBackEntity();
            if (hssfRow.getCell(0) != null)
            postBackEntity.setAdvName(hssfRow.getCell(0).getStringCellValue());
            if (hssfRow.getCell(1) != null)
            postBackEntity.setPrefix(String.valueOf((int) hssfRow.getCell(1).getNumericCellValue()));
            if (hssfRow.getCell(2) != null)
            postBackEntity.setTransactionId(String.valueOf((int) hssfRow.getCell(2).getNumericCellValue()));
            if (hssfRow.getCell(3) != null) {
                date = simpleDateFormat.parse(hssfRow.getCell(3).getStringCellValue());
                postBackEntity.setDate(new Date(date.getTime()));
                postBackEntity.setTime(new Time(date.getTime()));
            }
            if (hssfRow.getCell(4) != null)
            postBackEntity.setOfferName(hssfRow.getCell(4).getStringCellValue());
            if (hssfRow.getCell(5) != null)
            postBackEntity.setAfid((int) hssfRow.getCell(5).getNumericCellValue());
            if (hssfRow.getCell(6) != null)
            postBackEntity.setClickId(hssfRow.getCell(6).getStringCellValue());
            if (hssfRow.getCell(7) != null)
            postBackEntity.setSum(Double.parseDouble(hssfRow.getCell(7).getStringCellValue().replaceAll("[^\\d^\\.]", "")));
            if (hssfRow.getCell(8) != null)
            postBackEntity.setCurrency(hssfRow.getCell(8).getStringCellValue());
            if (hssfRow.getCell(9) != null)
            postBackEntity.setStatus(hssfRow.getCell(9).getStringCellValue());
            if (hssfRow.getCell(10) != null)
            postBackEntity.setGoal(hssfRow.getCell(10).getStringCellValue());
            if (hssfRow.getCell(11) != null)
            postBackEntity.setIDFA(hssfRow.getCell(11).getStringCellValue());
            if (hssfRow.getCell(12) != null)
            postBackEntity.setGaId(hssfRow.getCell(12).getStringCellValue());
            if (hssfRow.getCell(13) != null)
            postBackEntity.setT1(hssfRow.getCell(13).getStringCellValue());
            if (hssfRow.getCell(14) != null)
            postBackEntity.setT2(hssfRow.getCell(14).getStringCellValue());
            if (hssfRow.getCell(15) != null)
            postBackEntity.setT3(hssfRow.getCell(15).getStringCellValue());
            if (hssfRow.getCell(16) != null)
            postBackEntity.setT4(hssfRow.getCell(16).getStringCellValue());
            if (hssfRow.getCell(17) != null)
            postBackEntity.setT5(hssfRow.getCell(17).getStringCellValue());
            if (hssfRow.getCell(18) != null)
            postBackEntity.setT6(hssfRow.getCell(18).getStringCellValue());
            if (hssfRow.getCell(19) != null)
            postBackEntity.setT7(hssfRow.getCell(19).getStringCellValue());
            if (hssfRow.getCell(20) != null)
            postBackEntity.setT8(hssfRow.getCell(20).getStringCellValue());
            if (hssfRow.getCell(21) != null)
            postBackEntity.setT9(hssfRow.getCell(21).getStringCellValue());
            if (hssfRow.getCell(22) != null)
            postBackEntity.setT10(hssfRow.getCell(22).getStringCellValue());
            if (hssfRow.getCell(23) != null)
            postBackEntity.setSecretKey(hssfRow.getCell(23).getStringCellValue());
            if (hssfRow.getCell(24) != null)
            postBackEntity.setIpAddress(hssfRow.getCell(24).getStringCellValue());
            if (hssfRow.getCell(25) != null)
            postBackEntity.setEvent1(hssfRow.getCell(25).getStringCellValue());
            if (hssfRow.getCell(26) != null)
            postBackEntity.setEvent2(hssfRow.getCell(26).getStringCellValue());
            if (hssfRow.getCell(27) != null)
            postBackEntity.setEvent3(hssfRow.getCell(27).getStringCellValue());
            if (hssfRow.getCell(28) != null)
            postBackEntity.setEvent4(hssfRow.getCell(28).getStringCellValue());
            if (hssfRow.getCell(29) != null)
            postBackEntity.setEvent5(hssfRow.getCell(29).getStringCellValue());
            if (hssfRow.getCell(30) != null)
            postBackEntity.setEvent6(hssfRow.getCell(30).getStringCellValue());
            if (hssfRow.getCell(31) != null)
            postBackEntity.setEvent7(hssfRow.getCell(31).getStringCellValue());
            if (hssfRow.getCell(32) != null)
            postBackEntity.setEvent8(hssfRow.getCell(32).getStringCellValue());
            if (hssfRow.getCell(33) != null)
            postBackEntity.setEvent9(hssfRow.getCell(33).getStringCellValue());
            if (hssfRow.getCell(34) != null)
            postBackEntity.setEvent10(hssfRow.getCell(34).getStringCellValue());
            if (hssfRow.getCell(35) != null)
            postBackEntity.setIdc(hssfRow.getCell(35).getStringCellValue());
            if (hssfRow.getCell(36) != null)
            postBackEntity.setIdo(hssfRow.getCell(36).getStringCellValue());
            postBackEntity.setDuplicate("original");
            currencyEntity = MySQLDaoImpl.getInstance().getCurrency(postBackEntity.getCurrency());
            if (currencyEntity == null) currencyEntity = MySQLDaoImpl.getInstance().getCurrency("USD");
            exchangeEntity = MySQLDaoImpl.getInstance().getExchange(currencyEntity.getId());
            postBackEntity.setExchange(exchangeEntity.getId());
            if (MySQLDaoImpl.getInstance().getPostbacksByClickidPrefix(postBackEntity.getClickId(), postBackEntity.getPrefix()).isEmpty()) {
                MySQLDaoImpl.getInstance().addPostback(postBackEntity);
            }
            else{
                MySQLDaoImpl.getInstance().updatePostback(postBackEntity);
                //MySQLDaoImpl.getInstance().removePostback(postBackEntity);
            }

        }
        MySQLDaoImpl.getMasterDbSessionFactory().close();
    }
}
