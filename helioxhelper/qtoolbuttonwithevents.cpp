#include "QObject"
#include "QWidget"
#include <QProcess>
#include <QString>
#include <QEvent>
#include <QFocusEvent>
#include <QDebug>
#include <QSettings>
#include "qtoolbuttonwithevents.h"
#include <QToolTip>
#include <QTime>
#include <QEventLoop>
#include <QApplication>
#include <QTimer>

extern QProcess *readcommand;
extern bool pulseaudio;

QToolButtonWithEvents::QToolButtonWithEvents(QWidget *parent, QString settings1, QString settings2, QString lang) :
    QToolButton(parent)
{
    connect(this, SIGNAL(clicked()),
                 this, SLOT(buttonClickedFunction()));
    m_property = QString("");
    blockedSignals=false;
    //QString settings1 = "ProyectoHeliox";
    //QString settings2 = "HelioxHelper";
    settings = new QSettings(settings1, settings2);
    selectedLanguage=lang;
    speeching=false;

    setVisualStyle();

    this->setAutoRaise(true);
    installEventFilter(this);

    extern bool pulseaudio;
    if ( QBool(pulseaudio) == QBool(1) ){
        //qDebug() << "pulseaudio";

        audioplayer = new QString ("paplay");
    } else {
        //qDebug() << "alsa";

        audioplayer = new QString ("ogg123");
    }
  //  qDebug() << *audioplayer;

}


void QToolButtonWithEvents::readCaption( QString * label )
{
    speeching = true;

    connect(readcommand, SIGNAL(error(QProcess::ProcessError)), this, SLOT(setFalseSpeechingVar()));
    connect(readcommand, SIGNAL(destroyed()), this, SLOT(setFalseSpeechingVar()));
    connect(readcommand, SIGNAL(finished(int)), this, SLOT(setFalseSpeechingVar()));

    //extern QSettings settings;
    //qDebug() << "talking" << *label;

    if (settings->value("General/speechPath").toString() == ""){
        settings->setValue("General/speechPath", "/usr/share/helioxhelper/speech");
    }

//    QString lang = settings->value("General/Language").toString();

    // Read or play buttons in case of
    /*   button exec=firefox http://www.inali.com
    Will search and use by this order (stop if found)

  "firefox http://www.inali.com_idoma.ogg" ==> "firefox http:--www.inali.com_idoma.ogg"
    "firefox http://www.inali.com.ogg" ==> "firefox http:--www.inali.com.ogg"
        firefox_lang.ogg
          firefox.ogg
             screeenread  */


    QFile *filespeechStrip = new QFile(QString("%1/%2.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-").split(" ")[0]) );
    QFile *filespeechStripLang = new QFile(QString("%1/%2_%3.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-").split(" ")[0]).arg(selectedLanguage) );
    QFile *filespeechFull = new QFile(QString("%1/%2.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-")));
    QFile *filespeechFullLang = new QFile(QString("%1/%2_%3.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-")).arg(selectedLanguage));
    QFile *filespeechFolderFullLang = new QFile(QString("%1/%3/%2_%3.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-")).arg(selectedLanguage));
    QFile *filespeechFolderFullLangSpace = new QFile(QString("%1/%3/%2 %3.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-")).arg(selectedLanguage));
    QFile *filespeechFolderStripLang = new QFile(QString("%1/%3/%2.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-")).arg(selectedLanguage));
    QFile *filespeechFolderStripLangWithoutSpaces = new QFile(QString("%1/%3/%2.ogg").arg(settings->value("General/speechPath").toString()).arg(QString(m_propertyValue).replace("/","-").replace(" ","_")).arg(selectedLanguage));


    qDebug() << filespeechStrip->fileName();
    qDebug() << filespeechStripLang->fileName();
    qDebug() << filespeechFull->fileName();
    qDebug() << filespeechFullLang->fileName();
    qDebug() << filespeechFolderFullLang->fileName();
    qDebug() << filespeechFolderStripLang->fileName();
    qDebug() << filespeechFolderFullLangSpace->fileName();
    qDebug() << filespeechFolderStripLangWithoutSpaces->fileName();




    //Kill previous selection
   // QProcess *killreadcommand = new QProcess();
  //  killreadcommand->start("killall -9 paplay");
    //killreadcommand->waitForFinished(1000);

    if (readcommand->isOpen()){
       // qDebug() << "killing paplay";
        readcommand->close();
    }

    /*
    //desktop file to detect ; desktop name to start
    QList<QFile*> speechFiles;

    speechFiles[ filespeechFolderFullLang, filespeechFolderStripLang, filespeechFolderFullLangSpace, filespeechFolderStripLangWithoutSpaces, filespeechFullLang, filespeechFull, filespeechStripLang, filespeechStrip];
    int num=0;
    foreach( QFile file, speechFiles )  {
        qDebug(), file->fileName() << "playing";
    }
    */

    if  (filespeechFolderFullLang->exists()) {
        readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFolderFullLang->fileName()));
        qDebug() << filespeechFolderFullLang->fileName() << "playing";
    } else if  (filespeechFolderStripLang->exists()) {
            readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFolderStripLang->fileName()));
        //qDebug() << filespeechFolderStripLang->fileName() << "playing";
    } else if  (filespeechFolderFullLangSpace->exists()) {
            readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFolderFullLangSpace->fileName()));
        //qDebug() << filespeechFolderFullLangSpace->fileName() << "playing";
    } else if  (filespeechFolderStripLangWithoutSpaces->exists()) {
            readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFolderStripLangWithoutSpaces->fileName()));
        //qDebug() << filespeechFolderStripLangWithoutSpaces->fileName() << "playing";
    } else if  (filespeechFullLang->exists()) {
            readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFullLang->fileName()));
        //qDebug() << filespeechFullLang->fileName() << "playing";
    } else if (filespeechFull->exists()) {
        readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechFull->fileName()));
        //qDebug() << filespeechFull->fileName() << "playing";
    } else if (filespeechStripLang->exists()) {
        readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechStripLang->fileName()));
        //qDebug() << filespeechStripLang->fileName() << "playing";
    } else if (filespeechStrip->exists()) {
        readcommand->start(QString("%1 \"%2\"").arg(*audioplayer).arg(filespeechStrip->fileName()));
        //qDebug() << filespeechStrip->fileName() << "playing";
    } else {
    //if file doesn't exists any fallback OGG, read with speech-dispatcher
        readcommand->start(QString("spd-say \"%1\"").arg(*label));
        //qDebug() << "talking" << *label;
    }

}

void QToolButtonWithEvents::buttonClickedFunction()
{
    //qDebug() << "signal"; // << m_listNumber;
    emit buttonClicked(m_property, m_propertyValue);
}

void QToolButtonWithEvents::enterEvent( QEvent * event )
{
    event->accept();
    extern bool speech;
    if ( speech == 1 ){
        QString *txt = new QString(this->text());
        QString *desc = new QString(toolTipText);
        if (blockedSignals == false) {
            if (speeching == false){
                if (this->isVisible() == true){
                    if (*desc != ""){
                        this->readCaption(desc);
                   //    qDebug() << blockedSignals;
                    } else {
                        this->readCaption(txt);
                    }
                }
            }
        }
// else {
        //    qDebug() << "Shhh! No puedo hablar";
          //  qDebug() << blockedSignals;
      //  }
         //qDebug() << "talking by mouse enter... " << txt->toLocal8Bit()  << desc->toLocal8Bit();;
    }
}


void QToolButtonWithEvents::focusInEvent( QFocusEvent * event )
{
    event->accept();
    extern bool speech;
    if ( speech == 1 ){
        QString *txt = new QString(this->text());
        QString *desc = new QString(toolTipText);
        if (blockedSignals == false) {
            if (speeching == false){
                if (this->isVisible() == true){
                    if (*desc != ""){
                        this->readCaption(desc);
                   //    qDebug() << blockedSignals;
                    } else {
                        this->readCaption(txt);
                    }
                }
            }
        }
    //    txt->append(". ");
      //  txt->append(desc);

        //if (blockedSignals == false) {
          //  this->readCaption(txt);
         // //  qDebug() << blockedSignals;
       // } //else {
       //    // qDebug() << blockedSignals;
       //  //   qDebug() << "Shhh! No puedo hablar";
       //// }
       // qDebug() << "es este";
      //  qDebug() << event->type();


  //      qDebug() << "talking by focus event... " << txt->toLocal8Bit();

    }
}

void QToolButtonWithEvents::setTextProperty( QString *prop, QString *value)
{
    m_property = QString (*prop);
    m_propertyValue = QString (*value);
}

//workarround of  stylesheet  widget:focus  doesn't work
bool QToolButtonWithEvents::eventFilter(QObject* object,QEvent* event)

{
    if( event->type( ) == QEvent::FocusIn )
       {
           setStyleSheet(bNormalFocused+bHover+bPressed);
//           QToolTip::showText(QPoint(0,0),toolTipText);


       } else if ( event->type( ) == QEvent::FocusOut ) {
           setStyleSheet(bNormal+bHover+bPressed);
           QToolTip::hideText();
 //  } else if (event->type() == QEvent::HoverEnter) {
 //       QToolTip::showText(this->mapToGlobal(QPoint()),QString ("Help reminder sho : push Button"));
    } else if ((event->type() == QEvent::HoverLeave) || ( event->type( ) == QEvent::FocusOut ))  {
        QToolTip::hideText();
    }
    if ((event->type( ) == QEvent::KeyRelease ) || (event->type() == QEvent::HoverEnter)){
        this->showToolTip();
    }
    event->accept();


}


void QToolButtonWithEvents::setVisualStyle()
{
   //QSettings settings("ProyectoHeliox", "HelioxHelper");

    QString pixelsBorderNormal = settings->value("App Buttons/pixelsBorderNormal").toString();
    QString pixelsBorderFocused = settings->value("App Buttons/pixelsBorderFocused").toString();
    QString pixelsRoundedBorder = settings->value("App Buttons/pixelsRoundedBorder").toString();
    QString borderColorNormal = settings->value("App Buttons/borderColorNormal").toString();
    QString borderColorFocused = settings->value("App Buttons/borderColorFocused").toString();
    QString borderColorHovered = settings->value("App Buttons/borderColorHovered").toString();
    QString gradientBeginColor = settings->value("App Buttons/gradientBeginColor").toString();
    QString gradientEndColor = settings->value("App Buttons/gradientEndColor").toString();
    QString fontSize = settings->value("App Buttons/fontSize").toString();


    bNormal = QString("QToolButtonWithEvents {background-color: qlineargradient(x1: 1, y1: 0, x2: 1, y2: 1, stop: 0 %1, stop: 1 %2); border-style: solid ;"
                              "border-width: %3px; border-radius: %4px; border-color: %5; font: bold %6px; padding: 2px;}").arg(gradientBeginColor).arg(gradientEndColor).arg(pixelsBorderNormal).arg(pixelsRoundedBorder).arg(borderColorNormal).arg(fontSize);

    //Reversed exists cause QtoolbuttonwithEvents:Focus doesn't work (workarround fix)
    // for focus event - workarround CSS fix
    bNormalFocused = QString("QToolButtonWithEvents  { background-color: qlineargradient(x1: 1, y1: 0, x2: 1, y2: 1, stop: 0 %1, stop: 1 %2) ; border-style: solid;"
                             "border-width: %3px; border-radius: %4px; border-color: %5; font: bold %6px; }").arg(gradientBeginColor).arg(gradientEndColor).arg(pixelsBorderFocused).arg(pixelsRoundedBorder).arg(borderColorFocused).arg(fontSize);

    bHover= QString("QToolButtonWithEvents:hover  { background-color: qlineargradient(x1: 1, y1: 0, x2: 1, y2: 1, stop: 0 %1, stop: 1 %2) ; border-style: solid;"
                                    "border-width: %3px; border-radius: %4px; border-color: %5; font: bold %6px; }").arg(gradientBeginColor).arg(gradientEndColor).arg(pixelsBorderFocused).arg(pixelsRoundedBorder).arg(borderColorHovered).arg(fontSize);

    bPressed= QString("QToolButtonWithEvents:pressed  { background-color: qlineargradient(x1: 1, y1: 0, x2: 1, y2: 1, stop: 0 %2, stop: 1 %1) ; border-style: solid;"
                    "border-width: %3px; border-radius: %4px; border-color: %5; font: bold %6px; } ").arg(gradientBeginColor).arg(gradientEndColor).arg(pixelsBorderNormal).arg(pixelsRoundedBorder).arg(borderColorNormal).arg(fontSize);

    this->setStyleSheet(bNormal+bHover+bPressed);

    //this->setStyleSheet("QToolButtonWithEvents:hover  { background-color: qlineargradient(x1: 1, y1: 0, x2: 1, y2: 1, stop: 0 #a1bdd1, stop: 1 #d4d3d2) ; border-style: solid; border-width: 20px; border-radius: 3px; border-color: #679cd0; font: bold 13px; }");

}

void QToolButtonWithEvents::setToolTip(QString &label){

    toolTipText = label;
}

void QToolButtonWithEvents::showToolTip()
{
//    qDebug() << this->geometry();
//    qDebug() << this->geometry().y();
    int x = this->mapToGlobal( QPoint() ).x();
    int y = this->mapToGlobal(QPoint()).y();

    //int x = this->geometry().x()+this->geometry().width()/2;
    //int y = this->geometry().y()+this->geometry().height()/2;

    //int gy = this->geometry().height();
    //y = y + gy;
    QToolTip::showText(QPoint(x,y),toolTipText);
}

void QToolButtonWithEvents::setBlockedSignals(int value)
{
    blockedSignals = true;
    QTimer *timer = new QTimer;
    timer->setSingleShot(true);

    connect(timer, SIGNAL(timeout()), this, SLOT(reactivateBlockedSignals()));

    timer->start(value*1000);

}

void QToolButtonWithEvents::reactivateBlockedSignals(){
    blockedSignals = false;
}

void QToolButtonWithEvents::setSimpleBlockedSignals(bool value){
    blockedSignals = value;
}

void QToolButtonWithEvents::setFalseSpeechingVar(){
    speeching=false;
}
