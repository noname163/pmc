package com.utopia.pmc.ui;

import com.utopia.pmc.data.dto.response.transaction.TransactionResponse;

public class UI {
    public static String header() {
        return "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/css?family=Roboto|Varela+Round\">\r\n" + //
                "<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css\">\r\n" + //
                "<link rel=\"stylesheet\" href=\"https://fonts.googleapis.com/icon?family=Material+Icons\">\r\n" + //
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css\">\r\n" + //
                "<script src=\"https://code.jquery.com/jquery-3.5.1.min.js\"></script>\r\n" + //
                "<script src=\"https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js\"></script>\r\n" + //
                "<script src=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js\"></script>";

    }

    public static String style() {
        return "<style>\r\n" + //

                "        font-family : 'Varela Round', sans-serif;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                
                "    .modal-confirm {\r\n" + //
                "        color: #636363;\r\n" + //
                
                "        width: 325px;\r\n" + //
                "        font-size: 14px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .modal-content {\r\n" + //
                "        padding: 20px;\r\n" + //
                "        border-radius: 5px;\r\n" + //
                "        border: none;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .modal-header {\r\n" + //
                "        border-bottom: none;\r\n" + //
                "        position: relative;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm h4 {\r\n" + //
                "        text-align: center;\r\n" + //
                "        font-size: 26px;\r\n" + //
                "        margin: 30px 0 -15px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .form-control,\r\n" + //
                "    .modal-confirm .btn {\r\n" + //
                "        min-height: 40px;\r\n" + //
                "        border-radius: 3px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .close {\r\n" + //
                "        position: absolute;\r\n" + //
                "        top: -5px;\r\n" + //
                "        right: -5px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .modal-footer {\r\n" + //
                "        border: none;\r\n" + //
                "        text-align: center;\r\n" + //
                "        border-radius: 5px;\r\n" + //
                "        font-size: 13px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .icon-box {\r\n" + //
                "        color: #fff;\r\n" + //
                "        position: absolute;\r\n" + //
                "        margin: 0 auto;\r\n" + //
                "        left: 0;\r\n" + //
                "        right: 0;\r\n" + //
                "        top: -70px;\r\n" + //
                "        width: 95px;\r\n" + //
                "        height: 95px;\r\n" + //
                "        border-radius: 50%;\r\n" + //
                "        z-index: 9;\r\n" + //
                "        background: #82ce34;\r\n" + //
                "        padding: 15px;\r\n" + //
                "        text-align: center;\r\n" + //
                "        box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.1);\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .icon-box i {\r\n" + //
                "        font-size: 58px;\r\n" + //
                "        position: relative;\r\n" + //
                "        top: 3px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm.modal-dialog {\r\n" + //
                "        margin-top: 80px;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .btn {\r\n" + //
                "        color: #fff;\r\n" + //
                "        border-radius: 4px;\r\n" + //
                "        background: #82ce34;\r\n" + //
                "        text-decoration: none;\r\n" + //
                "        transition: all 0.4s;\r\n" + //
                "        line-height: normal;\r\n" + //
                "        border: none;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .modal-confirm .btn:hover,\r\n" + //
                "    .modal-confirm .btn:focus {\r\n" + //
                "        background: #6fb32b;\r\n" + //
                "        outline: none;\r\n" + //
                "    }\r\n" + //
                "\r\n" + //
                "    .trigger-btn {\r\n" + //
                "        display: inline-block;\r\n" + //
                "        margin: 100px auto;\r\n" + //
                "    }\r\n" + //
                "</style>";
    }
    public static String transactionBody(TransactionResponse transactionResponse){
        return " <div class=\"modal-dialog modal-confirm\">\r\n" + //
                "        <div class=\"modal-content\">\r\n" + //
                "            <div class=\"modal-header\">\r\n" + //
                "                <div class=\"icon-box\">\r\n" + //
                "                    <i class=\"material-icons\">&#xE876;</i>\r\n" + //
                "                </div>\r\n" + //
                "                <h4 class=\"modal-title w-100\">Successfull!</h4>\r\n" + //
                "            </div>\r\n" + //
                "            <div class=\"modal-body\">\r\n" + //
                "                <p class=\"text-center\">Plan: "+ transactionResponse.getPaymentPlan() +"</p>\r\n" + //
                "                <p class=\"text-center\">Amount: "+ transactionResponse.getAmount() +" VND</p>\r\n" + //
                "                <p class=\"text-center\">Transaction date: "+transactionResponse.getTransactioncreatedDate()+"</p>\r\n" + //
                "                <p class=\"text-center\">Payment date: " + transactionResponse.getTransactionPaymentDate()+"</p>\r\n" + //
                "            </div>\r\n" + //
                "            <div class=\"modal-footer\">\r\n" + //
                "                <button class=\"btn btn-success btn-block\" data-dismiss=\"modal\">OK</button>\r\n" + //
                "            </div>\r\n" + //
                "        </div>\r\n" + //
                "    </div>";
    }
    public static String successUI(TransactionResponse transactionResponse){
        return header()  + style() + transactionBody(transactionResponse) ;
    }
}
