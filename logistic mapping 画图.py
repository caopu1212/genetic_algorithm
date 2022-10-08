
import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
# data = {
#     'conventional GP': [0.7077339552968438, 0.9222907138496564, 0.9447761948973397, 0.8697004368644679, 0.8676962162399703, 0.8306908599525216, 0.2736135378950545, 0.9314857300720552, 0.7852019688894175, 0.8839684220327303, 0.7107223173832162, 0.9446759713320863, 0.9445880025706466, 0.8618384983845376, 0.8452912787267526, 0.8484082895797622, 0.7245613176380105, 0.865118297391487, 0.8018925073777512, 0.8871262498263799, 0.8296086423351965, 0.9240290027992079, 0.9720905356798825, 0.9217738162219914, 0.8506543776325544, 0.973693958458879, 0.8716741268361695, 0.8086036827505915, 0.7778368139190742, 0.8168518456161804, 0.8568550690483528, 0.6662728779477299, 0.9085399031959045, 0.9034835755024765, 0.8907687307888918, 0.8338333408578328, 0.8362716193880537, 0.7546475131143777, 0.8772886417073614, 0.8190110911533964, 0.8469861979175701, 0.8896138036216377, 0.9436708360495213, 0.9539074486939376, 0.8942290796950868, 0.8936860693369205, 0.768323759933675, 0.8208375009037401, 0.8429425877844734, 0.9548527273965162],
#     'RS-DPMOGP \\n A': [0.8589835742179421, 0.7148818791517786, 0.8737472357348423, 0.8505399075915158, 0.8903886981212831, 0.8556438199891911, 0.9342279480036345, 0.6903626253893425, 0.9591619823859604, 0.95406142175435, 0.8907577395666798, 0.8420226845656311, 0.9552607209187216, 0.9282449653953678, 0.8614353415846605, 0.9082623314846223, 0.8968877934826234, 0.7632867781208827, 0.8244688026141356, 0.9123483658896894, 0.9212115795941702, 0.9818891955109215, 0.8657525310668076, 0.9351602084522105, 0.9134353276940433, 0.9833888724721627, 0.9122516749931369, 0.9825996897047655, 0.9516419846723119, 0.8841045908426775, 0.8616877490594476, 0.8371998178985492, 0.7991222194609245, 0.927074474152964, 0.9669629364787479, 0.9219740280374137, 0.8777436511884662, 0.866446068773338, 0.9322134759730396, 0.8658955002617036, 0.7843789558677698, 0.8581451123792151, 0.8498529075095994, 0.9071020182829623, 0.9328013206686885, 0.8891881279858432, 0.9180862160143263, 0.8162533111333697, 0.9303115847185226, 0.8651000004222795],
#     'RS-DPMOGP B': [0.9347783877953161, 0.8626293449612028, 0.8915968678528097, 0.8839037496721829, 0.9140692315848084, 0.8599214213984723, 0.9287989501711605, 0.8743145511485291, 0.9182278454898625, 0.84947406824965, 0.8379335940723565, 0.9021539142569613, 0.9638428700091333, 0.9766445286450769, 0.9043440388989766, 0.9120745904332529, 0.8185987717514892, 0.8688111602151313, 0.860429439438086, 0.9484536999728687, 0.8415614024130342, 0.8412220911032967, 0.9343248800322324, 0.8023945076064593, 0.6598681700872457, 0.8240865642894979, 0.776346324518134, 0.8878583983417542, 0.8812319061034126, 0.8756775727131079, 0.7743507695678041, 0.9481273559287549, 0.8137851910558594, 0.8857128656291108, 0.955726026274021, 0.9315472366354999, 0.9148795868749247, 0.9445589227767845, 0.8818197359568717, 0.9211320361536735, 0.9364301156357834, 0.9175634943158626, 0.8976852462634718, 0.9351525135381619, 0.9012306304803629, 0.9361323550714529, 0.9311845184688112, 0.9577585456466018, 0.9079787127892819, 0.9883363486468242],
# }
# df = pd.DataFrame(data)
# df.plot.box(title="aa ")
#
#
# plt.grid(linestyle="--", alpha=0.3)
#
# plt.ylabel('R^2')
#
#
# plt.show()


def mapa(x0, R):
    """ definindo uma função chamada 'mapa', que retorna o valor de x1 para um dado x0 e R """
    return x0*R*(1-x0)


x1 = mapa(x0=0.1, R=3.4)
print("x1 =", x1)

x2 = mapa(x0=x1, R=3.4)
print("x2 =", x2)

x3 = mapa(x0=x2, R=3.4)
print("x3 =", x3)


def orbita(x0, R, maxiter):
    """ definindo uma função que retorna uma órbita, ou seja, retorna os valores de x1, x2, ..., xn a partir de um dado x0 e R."""

    #  inicializa uma lista com zeros em todas as posições
    x = [0]*(maxiter+1)

    # primeiro elemento da lista x recebe o valor x0
    x[0] = x0

    # preencho a lista x com os respectivos valores de x1, x2, x2, ..., xn
    for n in range(maxiter):
        x[n+1] = mapa(x[n], R)

    # retorna a lista x
    return x


x = orbita(x0=0.1, R=3.4, maxiter=3)
print(x)

# plotando o vetor x
import matplotlib.pyplot as plt # matplotlib é uma maneira de visualizar dados
plt.plot(x, marker='o')
plt.xlabel("índice")
plt.ylabel("valor de x")
plt.show()

# iterando a órbita 50 vezes
x = orbita(x0=0.1, R=3.4, maxiter=30)
plt.plot(x, marker='o')
plt.xlabel("índice")
plt.ylabel("valor de x")
plt.show()


import numpy as np
x0_list = np.linspace(0, 1, 30)
print(x0_list)

x1_list = mapa(x0=x0_list, R=3.4)

# gráfico de x1 em função de x0
plt.plot(x0_list, x1_list, marker='o')
plt.plot([0, 0.9], [0, 0.9]) # linha identidade
plt.xlabel('x0')
plt.ylabel('y0')
plt.show()


# gráfico em formato 'cobweb'
plt.plot(x0_list, x1_list)
plt.plot([0, 0.9], [0, 0.9]) # linha identidade
plt.xlabel('x0')
plt.ylabel('y0')

x = orbita(x0=0.1, R=3.4, maxiter=15)
for i in range(len(x)-1):
    plt.plot([x[i], x[i], x[i+1]], [x[i], x[i+1], x[i+1]], marker='o', color='red')

plt.show()


# gráfico em formato 'cobweb', onde ignoramos um tempo de transiente
plt.plot(x0_list, x1_list)
plt.plot([0, 0.9], [0, 0.9]) # linha identidade
plt.xlabel('x0')
plt.ylabel('y0')

x = orbita(x0=0.1, R=3.4, maxiter=100)
for i in range(80, len(x)-1):
    plt.plot([x[i], x[i], x[i+1]], [x[i], x[i+1], x[i+1]], marker='o', color='red')

plt.show()


# exemplo de órbita caótica
x1_list = mapa(x0=x0_list, R=3.95)

plt.plot(x0_list, x1_list)
plt.plot([0, 0.9], [0, 0.9]) # linha identidade
plt.xlabel('x0')
plt.ylabel('y0')

x = orbita(x0=0.1, R=3.95, maxiter=100)
for i in range(len(x)-1):
    plt.plot([x[i], x[i], x[i+1]], [x[i], x[i+1], x[i+1]], marker='o', color='red')

plt.show()


# criando um diagrama de bifurcação
R_list = np.linspace(1, 4, 1000)
transient = 1000
maxiter = 100
nponto = 0 # contador que é incrementado sempre que salvamos um novo ponto

# criamos dois arrays que salvarão os resultados obtidos
eixo_horizontal = np.zeros(len(R_list)*maxiter)
eixo_vertical = np.zeros(len(R_list)*maxiter)

for R in R_list:
    x0 = 0.2 # começamos sempre com x0=0.2

    # pulando o tempo de transiente
    for iter in range(transient):
        x0 = mapa(x0, R)

    # após pular o transiente, salvamos os pontos nos dois arrays
    # chamados eixo_horizontal e eixo_vertical
    for iter in range(maxiter):
        x0 = mapa(x0, R)

        eixo_horizontal[nponto] = R
        eixo_vertical[nponto] = x0
        nponto += 1

plt.xlabel('k')
plt.ylabel('x')
plt.plot(eixo_horizontal, eixo_vertical, 'o', markersize=0.1)
plt.show()