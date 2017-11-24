  #include "DHT.h"

  #define DHTTYPE DHT22
  const int analog = 0;
  const int pinHumiTemp = 2;
  float R2; //Résistance de la thermistance
  float U = 5.0;  //Tension du circuit
  float U2;     //Tension aux bornes de la thermistance
  float R1 = 10000.0; //Résistance du pont diviseur
  float temperature;  //Température ambiante à la Thermistance
  float coefA = 0.00088386; //Coeff A SteinhartHart
  float coefB = 0.0002583;  //Coeff B SteinhartHart
  float coefC = 0.00000011661; //Coeff C SteinhartHart

  DHT dht(pinHumiTemp, DHTTYPE);
  
void setup() {
  Serial.begin(9600);
  pinMode(pinHumiTemp, INPUT);
  dht.begin();
}

void loop() {
  // put your main code here, to run repeatedly:

  U2 = analogRead(analog)*5.0/1024.0;
  //Affichage de la tension aux bornes de la thermistance
  Serial.print("Tension aux bornes : ");
  Serial.println(U2);

  //On trouve la valeur de la résistance
  R2 = (U2 * R1)/(U - U2);
  Serial.print("Resistance Thermistance : ");
  Serial.println(R2);

  //On applique SteinhartHart pour trouver la température
  temperature = (1.0/(coefA + coefB*log(R2) + coefC*pow(log(R2),3)))-273.15;
  Serial.print("Temperature Thermistance : ");
  Serial.println(temperature);

  //Récupération température et humidité
  float h = dht.readHumidity();
  float t = dht.readTemperature();
  Serial.print("Humidite Capteur : ");
  Serial.print(h);
  Serial.print("  Temperature Capteur : ");
  Serial.println(t);
  
  delay(2000);
}
