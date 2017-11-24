void setup() {
  // put your setup code here, to run once:
  Serial.begin(9600);
}

void loop() {
  // put your main code here, to run repeatedly:
  if(Serial.available() > 0){
    int receive = Serial.read();
    if(receive == "1"){
      Serial.println("Active");
    }else if(receive == "0"){
      Serial.println("Unactive");
    }
//    Serial.print("Received : ");

//Serial.print(hum+";"+tIn+";"+tOut);

//    Serial.println(receive, DEC);
  }
  delay(1000);
}
