# TTest.py
# -*- coding: utf-8 -*-
'''
# Created on 2020-05-20 20:36
# TTest.py
# @author: huiwenhua
'''

## Import the packages
import numpy as np
from scipy import stats

def get_p_value(arrA, arrB):

    a = np.array(arrA)
    b = np.array(arrB)

    t, p = stats.ttest_ind(a,b)

    return p

if __name__ == "__main__":
    print(get_p_value([0.9277905009124382, 0.8481638608517377, 0.9012659320774578, 0.2592672909195082, 0.695534365733037, 0.8062502068883987, 0.9417635830221246, 0.9404088908779104, 0.6588640875684221, 0.8837322987842913, 0.8820165358815844, 0.9085948892361195, 0.8735293429971083, 0.703838731471983, 0.1614985856233353, 0.9324440628988515, 0.77453004135722, 0.957894496775394, 0.9274759860735622, 0.7869824381049456, 0.9739814470001313, 0.8467254075351665, 0.9172175813847117, 0.8975406633061312, 0.8027186866540685, 0.9711242367079225, 0.9502048862808599, 0.9565307421662409, 0.8937983889278449, 0.9695140144234754],
                      [0.9609706206907034, 0.8811947969299602, 0.9811305561827164, 0.9292796401124555, 0.9706992027075241, 0.8659846326420626, 0.9329614927328868, 0.9517820936795066, 0.9575852583382873, 0.6145353241892804, 0.9091068970361911, 0.9590309541476948, 0.4902164469164336, 0.7800787700655487, 0.8213306856793005, 0.86369502934903, 0.919730262829298, 0.9009688389997323, 0.9662881008664878, 0.9306827618064235, 0.8789243004462794, 0.8787647387227904, 0.9050325803439739, 0.990598919509413, 0.830315593533279, 0.9536474789367425, 0.9881151140247985, 0.951912495747818, 0.9477423197964824, 0.6859957374852499]))