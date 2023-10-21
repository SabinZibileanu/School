#include<iostream>
#include<list>
#include<cstring>
#include<fstream>
#include<cstdlib>
using namespace std;
ifstream fin("id.in");

int cautare_cuvant(char a[100])
{
    int frecventa = 0;
	ifstream inp;
	inp.open("clase.in");

	if(inp.is_open() )
	{

		string linie_fisier;
		while(inp >> linie_fisier)
		{
			if( a == linie_fisier)
                frecventa++;
		}

	}
    return frecventa;
}

struct Nod
{
float nota1,nota2,nota3,nota_teza;
char nume[50],clasa[50];
};

struct Nod citeste()
{
    struct Nod el;
    fin >> el.nota1 >> el.nota2 >> el.nota3 >> el.nota_teza >> el.clasa;
    fin.get(el.nume,50);
    fin.get();
    return el;
}

bool comparMedii(Nod &a,Nod &b)
{
    return (float)(a.nota1+a.nota2+a.nota3+a.nota_teza)/4 > (float)(b.nota1+b.nota2+b.nota3+b.nota_teza)/4;
}

bool lexicografic(Nod &a,Nod &b)
{
    if(strcmp(a.nume,b.nume) > 0)
        return a.nume < b.nume;
    else return b.nume < a.nume;
}

void catalog()
{       list <struct Nod> lista;
        float cnt,s=0;
        int nr = 0;
        char raspuns[4];
        for(int i = 0 ; i < 170 ; ++i)
        lista.push_back(citeste());

        lista.sort(lexicografic);

        for(std::list<Nod>::iterator it = lista.begin(); it != lista.end(); it++)
        {

        if(strcmp(it->nume,"") > 0)
        {
            if(it->nota1 > 10  || it->nota2 > 10 || it->nota3 > 10 || it->nota_teza > 10 || (it->nota1 * it->nota2 * it->nota3 * it->nota_teza == 0)|| atoi(it->clasa) > 12 || cautare_cuvant(it->clasa) == 0 || (it->nota1 * it->nota2 * it->nota3 * it->nota_teza < 0))
                {
                    cout << "Clasa elevului " << it->nume << " nu exista sau nu i se poate incheia situatia" << endl;
                    cout << "Verifica fisierul de greseli" << endl;
                    cout << endl;
                }

                else    {

                        cout << "Situatia elevului " << it->nume << " la clasa: " << it->clasa << endl;
                        cout << "Nota1: " << it->nota1 << endl;
                        cout << "Nota2: " << it->nota2 << endl;
                        cout << "Nota3: " << it->nota3 << endl;
                        cout << "Nota teza: " << it->nota_teza << endl;
                        cout << endl;
                        }
        }
        }

        lista.sort(comparMedii);
        cout << "Aici este situatia pe liceu:" << endl;
        cout << "Daca nu apar toti elevii din liceu se poate verifica fisierul de greseli." << endl;
        cout << endl;

        for(std::list<Nod>::iterator it = lista.begin(); it != lista.end(); it++)
        {   if(strcmp(it->nume,"")>0)
                {    if(it->nota1 <= 10 && it->nota2 <= 10 && it->nota3 <= 10 && it->nota_teza <= 10 && (it->nota1 * it->nota2 * it->nota3 * it->nota_teza != 0) && atoi(it->clasa) <= 12 && cautare_cuvant(it->clasa) != 0 && (it->nota1 * it->nota2 * it->nota3 * it->nota_teza > 0))
                        {
                            cnt = (float)(it->nota1 + it->nota2 + it->nota3+it->nota_teza)/4;
                            s = s + cnt;
                            nr++;
                            if(cnt < 5)
                                cout << it->nume << " corigent la clasa " << it->clasa << " cu media " << cnt << endl;

                            else
                                cout << it->nume << " Media: " << cnt << " la clasa " << it->clasa << endl;

                        }
                }
        }
        cout << endl;
        cout << "Media liceului este " << (float)(s/nr) << endl;
        cout << "Doresti sa vezi situatia la o anumita clasa?Tasteaza numele clasei daca da si nu daca vrei sa iesi" << endl;
        cout << "Raspuns:";
        cin >> raspuns;
        cout << endl;

        if(strcmp(raspuns,"nu"))
        {   if(cautare_cuvant(raspuns) != 0)
            {
                for(std::list<Nod>::iterator it = lista.begin(); it != lista.end(); it++)
        {       if(strcmp(it->nume,"")>0)
                {
                    if(it->nota1 <= 10 && it->nota2 <= 10 && it->nota3 <= 10 && it->nota_teza <= 10 && (it->nota1 * it->nota2 * it->nota3 * it->nota_teza != 0) && (it->nota1 * it->nota2 * it->nota3 * it->nota_teza > 0))
                    {
                            if(strcmp(it->clasa,raspuns) == 0)
                            {   cnt = (float)(it->nota1 + it->nota2 + it->nota3+ it->nota_teza)/4;
                            if(cnt < 5)
                                cout << it->nume << " corigent cu media " << cnt << endl;

                            else
                                cout << it->nume << " Media: " <<  cnt << endl;

                            }

                    }
                    else
                        if(strcmp(it->clasa,raspuns) == 0)
                        cout << it->nume << " : situatie neincheiata" << endl;

                }
        }

            }
            else
                cout << "Clasa nu exista" << endl;
        }
        else
            cout << "Multumim pentru folosirea catalogului" << endl;
}

int main()
{   ifstream input;
    size_t pos;
    string line,citit;
    unsigned int inc = 0;
    cout << "\n\n\tAcesta este un catalog online pentru profesorii de informatica :)" << endl;
    cout << "\n\n\tPentru inceput introdu parola care a fost oferita fiecarui profesor pentru a putea edita catalogul" << endl;
    cout << "Parola:";
    cin >> citit;
    cout << endl;

    input.open("test.in");
    if(input.is_open())
{
    while(getline(input,line))
    {
       pos = line.find(citit);
       if(pos == string::npos || citit != line)
       {
           do
           {   inc++;
               cout << "Gresit" << endl;
               cin >> citit;
               pos = line.find(citit);
               if(inc >= 4 && citit != line)
               {
                   cout << "Prea multe incercari.Da un refresh" << endl;
                   return 0;
               }
           }while(pos == string::npos || citit != line);

       }
       if(pos != string::npos && citit == line)
        {   cout << "\n\n\tBun venit.Fisierul unde poti edita situatiile se numeste id.in" << endl;
            cout << endl;
            catalog();
            break;
        }
    }
}

    return 0;
}
