import sys
import cv2
import math
import numpy as np
from scipy.sparse import linalg
from PyQt5.QtCore import *
from PyQt5.QtGui import *
from PyQt5.QtWidgets import (
    QApplication,
    QCheckBox,
    QComboBox,
    QDateEdit,
    QDateTimeEdit,
    QDial,
    QDialog,
    QDoubleSpinBox,
    QFontComboBox,
    QGridLayout,
    QLabel,
    QLCDNumber,
    QLineEdit,
    QMainWindow,
    QProgressBar,
    QPushButton,
    QRadioButton,
    QSlider,
    QSpinBox,
    QTimeEdit,
    QVBoxLayout,
    QHBoxLayout,
    QWidget,
    QFileDialog,
    QMessageBox
    
)
import win32api
import statistics
from grafic import InterfataStat


class InterfataPrincipala(QMainWindow):

    def __init__(self,parent=None):
        
        
        super(InterfataPrincipala,self).__init__(parent)
        self.caleBD = "E:\X-Proiecte si scoala\ACS\Laborator1\BazaORL"
        self.poza = self.caleBD + "s"
        self.nrPers = 40
        self.nrPozeAntrenate = 8 # implicit
        self.nrPixeli = 112*92
        self.nr_total_teste = 80
        

        self.setWindowTitle("Modul 3 ACS")
        labelTitlu = QLabel("Alege un algoritm: ")
        self.labelConfig = QLabel("Alege o norma: ")
        self.poza_aleasa = QLabel()
        self.labelK = QLabel("Alege un k:")
        self.labelAntrenare = QLabel("Alege antrenarea pozelor:")
        self.butonEFClasic = QRadioButton("Algortimul EigenFaces Clasic")
        self.butonEFClasa = QRadioButton("Algortimul EigenFaces ReprezentantiClasa")
        self.butonLanczos = QRadioButton("Algortimul Lanczos")
        self.butonNN = QRadioButton("Algortimul Nearest-Neighbor")
        self.butonKNN = QRadioButton("Algortimul K-Nearest-Neighbor")
        self.Rulare = QPushButton('')
        self.label_rezultat = QLabel(self)
        self.label_tine_minte_cale = QLabel('')
        
        
        self.alege_pers = QPushButton('Alege persoana')
        self.alege_pers.setVisible(False)
        self.statistici = QPushButton('Vezi statistici')
        self.statistici.setVisible(False)

        self.panel_stat = InterfataStat(self)

        self.Rulare.setVisible(False)
        self.Rulare.setEnabled(True)
        self.statistici.setVisible(False)
        self.butonEFClasic.clicked.connect(self.alegere_algoritm)
        self.butonEFClasa.clicked.connect(self.alegere_algoritm)
        self.butonLanczos.clicked.connect(self.alegere_algoritm)
        self.butonNN.clicked.connect(self.alegere_algoritm)
        self.butonKNN.clicked.connect(self.alegere_algoritm)
        self.statistici.clicked.connect(self.arata_statistici)
        

        self.Rulare.clicked.connect(self.rulare)
        self.alege_pers.clicked.connect(self.getPoza)
        
        self.combo_box_norme = QComboBox()
        self.combo_box_norme.setGeometry(200, 150, 120, 30)
        lista_norme = ['1', '2', 'infinit', 'cos']
        lista_k = ['20','40','60','80','100']
        lista_knn = ['3','5','7','9']
        lista_antrenare = ['60% Antrenare','80% Antrenare', '90% Antrenare']
       
        self.combo_box_norme.addItems(lista_norme)
        self.combo_box_norme.setVisible(False)
        self.labelConfig.setVisible(False)

        self.combo_box_k = QComboBox()
        self.combo_box_k.setGeometry(200, 150, 120, 30)

        self.combo_box_knn = QComboBox()
        self.combo_box_knn.setGeometry(200, 150, 120, 30)

        self.combo_box_antrenare = QComboBox()
        self.combo_box_antrenare.setGeometry(200, 150, 120, 30)
        
        self.combo_box_k.addItems(lista_k)
        self.labelK.setVisible(False)
        self.combo_box_k.setVisible(False)
        self.combo_box_knn.addItems(lista_knn)
        self.combo_box_knn.setVisible(False)
        self.combo_box_antrenare.addItems(lista_antrenare)
        self.combo_box_antrenare.setVisible(False)
        self.labelAntrenare.setVisible(False)

        layout_cbox_k = QHBoxLayout()
        layout_cboxnorme = QHBoxLayout()
        layout_principal = QGridLayout()
        layout_cbox_antrenare = QHBoxLayout()
        
        layout_label = QHBoxLayout()
        layout_label.addWidget(labelTitlu)

        layout_butoane = QVBoxLayout()
        layout_butoane.addWidget(self.butonEFClasa)
        layout_butoane.addWidget(self.butonEFClasic)
        layout_butoane.addWidget(self.butonLanczos)
        layout_butoane.addWidget(self.butonNN)
        layout_butoane.addWidget(self.butonKNN)

        layout_poze = QHBoxLayout()
        layout_poze.addWidget(self.poza_aleasa)
        layout_poze.addWidget(self.label_rezultat)

        layout_cboxnorme.addWidget(self.labelConfig)
        layout_cboxnorme.addWidget(self.combo_box_norme)
        layout_cbox_k.addWidget(self.labelK)
        layout_cbox_k.addWidget(self.combo_box_k)
        layout_cbox_k.addWidget(self.combo_box_knn)
        layout_cbox_antrenare.addWidget(self.labelAntrenare)
        layout_cbox_antrenare.addWidget(self.combo_box_antrenare)


        layout_principal.addLayout(layout_label,0,0)
        layout_principal.addLayout(layout_butoane,1,0)
        layout_principal.addLayout(layout_cboxnorme,2,0)
        layout_principal.addLayout(layout_cbox_k,3,0)
        layout_principal.addLayout(layout_cbox_antrenare,4,0)
        layout_principal.addWidget(self.statistici,5,0)
        layout_principal.addWidget(self.alege_pers,6,0)
        layout_principal.addWidget(self.Rulare,7,0)
        layout_principal.addWidget(self.statistici,8,0)
        layout_principal.addLayout(layout_poze,1,10)

        widget = QWidget()
        widget.setLayout(layout_principal)

        self.setCentralWidget(widget)

        self.show()

    def alegere_algoritm(self):
        contor = 0
        if self.butonEFClasic.isChecked():
            self.alege_pers.setVisible(True)
            self.statistici.setVisible(True)
            self.label_rezultat.clear()
            self.Rulare.setVisible(True)
            self.statistici.setVisible(True)
            self.labelConfig.setVisible(True)
            self.combo_box_norme.setVisible(True)
            self.combo_box_k.setVisible(True)
            self.combo_box_knn.setVisible(False)
            self.labelK.setVisible(True)
            self.combo_box_antrenare.setVisible(True)
            self.labelAntrenare.setVisible(True)
            self.Rulare.setText("Ruleaza EigenFaces Clasic")

        elif self.butonEFClasa.isChecked():
            self.alege_pers.setVisible(True)
            self.statistici.setVisible(True)
            self.label_rezultat.clear()
            self.Rulare.setVisible(True)
            self.statistici.setVisible(True)
            self.labelConfig.setVisible(True)
            self.combo_box_norme.setVisible(True)
            self.labelK.setVisible(True)
            self.combo_box_k.setVisible(True)
            self.combo_box_knn.setVisible(False)
            self.combo_box_antrenare.setVisible(True)
            self.labelAntrenare.setVisible(True)
            self.Rulare.setText("Ruleaza EigenFaces ReprezentantiClasa")
            contor = 1

        elif self.butonLanczos.isChecked():
            self.alege_pers.setVisible(True)
            self.statistici.setVisible(True)
            self.label_rezultat.clear()
            self.Rulare.setVisible(True)
            self.labelConfig.setVisible(True)
            self.combo_box_norme.setVisible(True)
            self.labelK.setVisible(True)
            self.combo_box_k.setVisible(True)
            self.combo_box_knn.setVisible(False)
            self.combo_box_antrenare.setVisible(True)
            self.labelAntrenare.setVisible(True)
            self.Rulare.setText("Ruleaza Lanczos")
            contor = 2
        
        elif self.butonNN.isChecked():
            self.alege_pers.setVisible(True)
            self.statistici.setVisible(True)
            self.label_rezultat.clear()
            self.Rulare.setVisible(True)
            self.labelConfig.setVisible(True)
            self.combo_box_norme.setVisible(True)
            self.labelK.setVisible(False)
            self.combo_box_k.setVisible(False)
            self.combo_box_knn.setVisible(False)
            self.combo_box_antrenare.setVisible(True)
            self.labelAntrenare.setVisible(True)
            self.Rulare.setText("Ruleaza NN")
            contor = 3
        
        else:
            self.alege_pers.setVisible(True)
            self.statistici.setVisible(True)
            self.label_rezultat.clear()
            self.Rulare.setVisible(True)
            self.labelConfig.setVisible(True)
            self.combo_box_norme.setVisible(True)
            self.labelK.setVisible(True)
            self.combo_box_k.setVisible(False)
            self.combo_box_knn.setVisible(True)
            self.combo_box_antrenare.setVisible(True)
            self.labelAntrenare.setVisible(True)
            self.Rulare.setText("Ruleaza KNN")
            contor = 4

        return contor

    def configurareA(self):
        A = np.zeros((self.nrPixeli,self.nrPozeAntrenate * self.nrPers))
        
        for i in range(1,self.nrPers+1):
            caleFolder = self.caleBD + '\s' + str(i) + '\\'
            for j in range(1,self.nrPozeAntrenate + 1):
                calePoza = caleFolder + str(j) + '.pgm'
                poza = cv2.imread(calePoza,0)
                poza = np.array(poza)
                          
                pozaVect = np.reshape(poza,(10304,))
                A[:,(i - 1) * self.nrPozeAntrenate+ (j - 1)] = pozaVect
        return A

    def NN(self,A,pozaTest,norma):
    
        z = np.zeros(len(A[0]))
        
        for i in range (A.shape[1]):
            if(norma == 'cos'):
                z[i] = 1-(np.dot(A[:,i],pozaTest) / (np.linalg.norm(A[:,i]) * np.linalg.norm(pozaTest)))
            else:
                z[i] = np.linalg.norm(A[:,i] - pozaTest,norma)
        pozitia = np.argmin(z)
        return pozitia

    
    def K_NN(self,A,pozaTest,norma,k):
    
        z = np.zeros(len(A[0]))
        
        for i in range (A.shape[1]):
            if(norma == 'cos'):
                z[i] = 1-(np.dot(A[:,i],pozaTest) / (np.linalg.norm(A[:,i]) * np.linalg.norm(pozaTest)))
            else:
                z[i] = np.linalg.norm(A[:,i] - pozaTest,norma)
        pozitii = np.argsort(z)
        pozitii = pozitii[:k]
        persoane = pozitii//self.nrPozeAntrenate + 1
        persoana = statistics.mode(persoane)
        return (persoana - 1) * self.nrPozeAntrenate
    
    def preprocesare_EFclasic(self,A,k):
        media = np.mean(A,axis = 1) 
        A=(A.T-media).T 
        L = A.T@A
        d, v = linalg.eigs(L,k)
        v = A@v
        indici = np.argsort(d) 
        indici = indici[-1:-k-1:-1] 
        HQPB = v[:,indici] 
        proiectii = A.T @ HQPB 
        return [media,proiectii,HQPB]

    def preprocesare_EFClasa(self,A,k):
        
        RC = np.zeros((10304,40))
        for i in range(40):
            RC[:,i] = np.mean(A[:,((i - 1) * self.nrPozeAntrenate):((i * self.nrPozeAntrenate) - 1)],axis=1)
        media = np.mean(RC,axis = 1)   

        RC = (RC.T - media).T
        cov = RC.T@RC
        d, v = np.linalg.eig(cov)
        v = RC@v
        indici = np.argsort(d)
        indici = indici[-1:-k-1:-1]
        HQPB = v[:,indici]
        proiectii = RC.T @ HQPB
        return [media,proiectii,HQPB]

    def Lanczos(self,A,k):
        m = 10304
        q = np.zeros((m,k+2))
        q[:,0] = np.zeros(m)
        q[:,1] = np.ones(m)
        q[:,1] = q[:,1] / np.linalg.norm(q[:,1])
        B = 0
        q[0] = 0
        for i in range (1,k + 1):
            w = A @ (A.T@q[:,i]) - B * q[:,i-1]
            l = np.dot(w,q[:,i])
            w = w - l*q[:,i]
            B = np.linalg.norm(w, )
            q[:,i+1]=w / B
        HQPB=q[:,2:] 
        proiectii = A.T @ HQPB
        
        return proiectii, HQPB

    def stabilire_antrenare(self):
        if self.combo_box_antrenare.currentText() == '80% Antrenare':
            antrenate = 8
        elif self.combo_box_antrenare.currentText() == '60% Antrenare':
            antrenate = 6
        else:
            antrenate = 9
        return antrenate

    def rulare(self):
        
        #k = int(self.combo_box_k.currentText())
        norma = self.combo_box_norme.currentText()
        if norma == 'infinit':
            norma = math.inf
            
        elif norma == '1' or norma == '2':
            norma = int(norma)
        
        self.nrPozeAntrenate = self.stabilire_antrenare()
        
        A = self.configurareA()
       
        if self.alegere_algoritm() == 0:
            
            k = int(self.combo_box_k.currentText())
            media,proiectii,HQPB = self.preprocesare_EFclasic(A,k)
            cale = self.label_tine_minte_cale.text()
            
            if(len(cale) == 0):
                win32api.MessageBox('0','Alege poza!!!','Eroare')
                return

            x = cale.split("/")
            nrPoza = x[len(x)-1].split(".")
            nrPoza = int(nrPoza[0])
            if(nrPoza <= self.nrPozeAntrenate):
                win32api.MessageBox('0',f'Alege o poza de la {self.nrPozeAntrenate+1} in sus,deoarece poza aleasa {nrPoza} este de antrenare!','Eroare')
                return
            
            pozaTest = cv2.imread(cale,0)
            pozaTest = np.array(pozaTest)
            #cv2.imshow('Aleasa',pozaTest)
            pozaTest = pozaTest.reshape((-1,))
            pozaTest = pozaTest - media
            pr_test = pozaTest @ HQPB
            index = self.NN(proiectii.T,pr_test,norma)
            img = 1
            index //= self.nrPozeAntrenate
            index += 1
            cale_poza_prezisa = self.caleBD + f'\s{index}\{img}.pgm'
            poza_prezisa = cv2.imread(cale_poza_prezisa)
            
            self.pixmap = QPixmap(cale_poza_prezisa)
            self.label_rezultat.setPixmap(self.pixmap)
            self.label_rezultat.resize(self.pixmap.width(),
						self.pixmap.height())

        elif self.alegere_algoritm() == 1:
            k = int(self.combo_box_k.currentText())
            media,proiectii,HQPB = self.preprocesare_EFClasa(A,k)
            cale = self.label_tine_minte_cale.text()
            if(len(cale) == 0):
                win32api.MessageBox('0','Alege poza!!!','Eroare')
                return
            
            x = cale.split("/")
            nrPoza = x[len(x)-1].split(".")
            nrPoza = int(nrPoza[0])
            if(nrPoza <= self.nrPozeAntrenate):
                win32api.MessageBox('0',f'Alege o poza de la {self.nrPozeAntrenate+1} in sus,deoarece poza aleasa {nrPoza} este de antrenare!','Eroare')
                return

            pozaTest = cv2.imread(cale,0)
            pozaTest = np.array(pozaTest)
            pozaTest = pozaTest.reshape((-1,))
            pozaTest = pozaTest - media
            pr_test = pozaTest @ HQPB
            index = self.NN(proiectii.T,pr_test,norma)
            if index == 0:
                index = 40
            img = 1
           
            cale_poza_prezisa = self.caleBD + f'\s{index}\{img}.pgm'
            poza_prezisa = cv2.imread(cale_poza_prezisa)
            
            self.pixmap = QPixmap(cale_poza_prezisa)
            self.label_rezultat.setPixmap(self.pixmap)
            self.label_rezultat.resize(self.pixmap.width(),
						self.pixmap.height())

        elif self.alegere_algoritm() == 2:
            k = int(self.combo_box_k.currentText())
            proiectii,HQPB = self.Lanczos(A,k)
            cale = self.label_tine_minte_cale.text()
            if(len(cale) == 0):
                win32api.MessageBox('0','Alege poza!!!','Eroare')
                return
            
            x = cale.split("/")
            nrPoza = x[len(x)-1].split(".")
            nrPoza = int(nrPoza[0])
            if(nrPoza <= self.nrPozeAntrenate):
                win32api.MessageBox('0',f'Alege o poza de la {self.nrPozeAntrenate+1} in sus,deoarece poza aleasa {nrPoza} este de antrenare!','Eroare')
                return

            pozaTest = cv2.imread(cale,0)
            pozaTest = np.array(pozaTest)
            
            pozaTest = pozaTest.reshape((-1,))
            pr_test = pozaTest @ HQPB
            index = self.NN(proiectii.T,pr_test,norma)
            img = 1
            index //= self.nrPozeAntrenate
            index += 1
            cale_poza_prezisa = self.caleBD + f'\s{index}\{img}.pgm'
            poza_prezisa = cv2.imread(cale_poza_prezisa)
            
            self.pixmap = QPixmap(cale_poza_prezisa)
            self.label_rezultat.setPixmap(self.pixmap)
            self.label_rezultat.resize(self.pixmap.width(),
						self.pixmap.height())
            

        elif self.alegere_algoritm() == 3:
            k = 1
            cale = self.label_tine_minte_cale.text()
            if(len(cale) == 0):
                win32api.MessageBox('0','Alege poza!!!','Eroare')
                return

            x = cale.split("/")
            nrPoza = x[len(x)-1].split(".")
            nrPoza = int(nrPoza[0])
            if(nrPoza <= self.nrPozeAntrenate):
                win32api.MessageBox('0',f'Alege o poza de la {self.nrPozeAntrenate+1} in sus,deoarece poza aleasa {nrPoza} este de antrenare!','Eroare')
                return
            
            pozaTest = cv2.imread(cale,0)
            pozaTest = np.array(pozaTest)
            pozaTest = pozaTest.reshape((-1,))
            index = self.NN(A,pozaTest,norma)
            index //= self.nrPozeAntrenate
            img = index % self.nrPozeAntrenate + 1
            index += 1
            cale_poza_prezisa = self.caleBD + f'\s{index}\{img}.pgm'
            poza_prezisa = cv2.imread(cale_poza_prezisa)
            
            self.pixmap = QPixmap(cale_poza_prezisa)
            self.label_rezultat.setPixmap(self.pixmap)
            self.label_rezultat.resize(self.pixmap.width(),
						self.pixmap.height())
        
        else:
            k = int(self.combo_box_knn.currentText())
            cale = self.label_tine_minte_cale.text()
            if(len(cale) == 0):
                win32api.MessageBox('0','Alege poza!!!','Eroare')
                return
            
            x = cale.split("/")
            nrPoza = x[len(x)-1].split(".")
            nrPoza = int(nrPoza[0])
            if(nrPoza <= self.nrPozeAntrenate):
                win32api.MessageBox('0',f'Alege o poza de la {self.nrPozeAntrenate+1} in sus,deoarece poza aleasa {nrPoza} este de antrenare!','Eroare')
                return

            pozaTest = cv2.imread(cale,0)
            pozaTest = np.array(pozaTest)
            pozaTest = pozaTest.reshape((-1,))
            index = self.K_NN(A,pozaTest,norma,k)
            index //= self.nrPozeAntrenate
            img = index % self.nrPozeAntrenate + 1
            index += 1
            cale_poza_prezisa = self.caleBD + f'\s{index}\{img}.pgm'
            poza_prezisa = cv2.imread(cale_poza_prezisa)
            
            self.pixmap = QPixmap(cale_poza_prezisa)
            self.label_rezultat.setPixmap(self.pixmap)
            self.label_rezultat.resize(self.pixmap.width(),
						self.pixmap.height())


    def getPoza(self):
        self.label_rezultat.clear()
        
        fname = QFileDialog.getOpenFileName(self, 'Open file', 
         'E:\X-Proiecte si scoala\ACS\Laborator1\BazaORL',"Image files (*.pgm)")
        self.poza_aleasa.setPixmap(QPixmap(fname[0]))
        
        self.label_tine_minte_cale.setText(fname[0])
    
    def arata_statistici(self):
        self.panel_stat.show()
        
   
if __name__ == '__main__':
    
    app = QApplication(sys.argv)

    main = InterfataPrincipala()
    
    main.show()
    
    sys.exit(app.exec_())  