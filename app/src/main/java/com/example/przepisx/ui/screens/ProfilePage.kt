package com.example.przepisx.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.example.przepisx.AuthState
import com.example.przepisx.AuthViewModel
import com.example.przepisx.R

@Composable
fun ProfilePage(modifier: Modifier = Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState = authViewModel.authState.observeAsState()

    LaunchedEffect(authState.value) {
        when(authState.value) {
            is AuthState.Unauthenticated -> navController.navigate("login")
            else -> Unit
        }
    }
    Column(
        modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(color = Color(android.graphics.Color.parseColor("#f2f1f6"))),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ConstraintLayout(modifier
            .height(250.dp)
            .background(color = Color(android.graphics.Color.parseColor("#32357a")))) {
            val (topImg, profile, title, back, pen) = createRefs()

            Image(painterResource(id = R.drawable.arc_3), null, modifier
                .fillMaxWidth()
                .constrainAs(topImg) {
                    bottom.linkTo(parent.bottom)
                })

            Image(painterResource(id = R.drawable.awatar1), null, modifier
                .fillMaxWidth()
                .constrainAs(profile) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(topImg.bottom)
                })

            Text(text = "Profil",
                style = TextStyle(color = Color.White,
                    fontSize = 30.sp),
                modifier = modifier
                    .constrainAs(title) {
                        top.linkTo(parent.top, margin = 32.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        Text(
            text = "Jan Kowalski",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 16.dp),
            color = Color(android.graphics.Color.parseColor("#32357a"))
        )

        Text(
            text = "janKowalski@gmail.com",
            fontSize = 18.sp,
            color = Color(android.graphics.Color.parseColor("#747679"))
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_3), null, modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {})
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)) {
                Text(
                    text = "Edytuj Zdjecie",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable {  }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_4), null, modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {})
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)) {
                Text(
                    text = "Moje Przepisy",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    Modifier
                        .padding(end = 5.dp)
                        .clickable {  }
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, end = 32.dp, top = 32.dp, bottom = 10.dp)
                .height(55.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Image(painter = painterResource(id = R.drawable.btn_6), null, modifier = Modifier
                    .padding(end = 5.dp)
                    .clickable {})
            }
            Column(modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)) {
                Text(
                    text = "Wyloguj",
                    color = Color.Black,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.arrow),
                    null,
                    modifier
                        .padding(end = 5.dp)
                        .clickable {
                            authViewModel.signout()
                        }
                )
            }
        }
    }
}
